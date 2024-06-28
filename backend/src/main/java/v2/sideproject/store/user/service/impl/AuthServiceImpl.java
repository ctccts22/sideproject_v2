package v2.sideproject.store.user.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import v2.sideproject.store.jwt.TokenProvider;
import v2.sideproject.store.user.constants.AuthConstants;
import v2.sideproject.store.user.dto.LoginDto;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.AuthService;
import v2.sideproject.store.user.userDetails.CustomUserDetails;
import v2.sideproject.store.user.vo.UsersInfoResponseVo;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private static final long maxAgeForCookie = 7 * 24 * 60 * 60;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final UsersRepository usersRepository;
    private final CustomUserDetails customUserDetails;


    @Override
    @Transactional
    public void login(LoginDto loginDto, HttpServletResponse response) {

        usersRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(AuthConstants.MESSAGE_404));

        Authentication authentication;
        try{
            authentication =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = tokenProvider.createAccessToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);

            response.addHeader("Authorization", "Bearer " + accessToken);
            tokenProvider.createRefreshTokenCookie(response, "refreshToken", refreshToken, maxAgeForCookie);


        }catch(BadCredentialsException e){
            throw new BadCredentialsException(AuthConstants.MESSAGE_401);
        }
    }



    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = customUserDetails.loadUserByUsername(authentication.getName());

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<String> cookieValue = Arrays.stream(cookies)
                    .filter(c -> "refreshToken".equals(c.getName()) && hasText(c.getValue()))
                    .map(Cookie::getValue)
                    .findFirst();

            cookieValue.ifPresent(value -> {
                tokenProvider.deleteRefreshTokenCookie(response, "refreshToken");
                // refreshToken delete
                redisTemplate.delete(value);
            });
        }
        // accessToken delete
        redisTemplate.delete(userDetails.getUsername());
    }


    // acess token redis에 있는지 확인 필요 email check 필요
    // 로그아웃 상태일때 헤더에는 토큰이 없으므로 UsernameNotFoundExceptiond에 걸림
    // Authentication 클래스는 헤더에서 AccessToken를 가져옴
    @Override
    @Transactional
    public void getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = customUserDetails.loadUserByUsername(authentication.getName());

        var usersInfo = usersRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String checkToken = redisTemplate.opsForValue().get(usersInfo.getEmail());
        if (!hasText(checkToken)) {
            throw new RuntimeException("test2");
        }


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<String> cookieValue = Arrays.stream(cookies)
                    .filter(c -> "refreshToken".equals(c.getName()) && hasText(c.getValue()))
                    .map(Cookie::getValue)
                    .findFirst();

            cookieValue.ifPresentOrElse(
                    refreshToken -> {
                        String accessToken = tokenProvider.searchAccessTokenByRefreshToken(refreshToken);
                        if (accessToken != null) {
                            log.info("Access token found: {}", accessToken);
                            response.setHeader("Authorization", "Bearer " + accessToken);
                        } else {
                            handleMissingRefreshToken();
                        }
                    },
                    this::handleMissingRefreshToken);
        } else {
            handleMissingRefreshToken();
        }
    }

    @Override
    @Transactional
    public UsersInfoResponseVo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = customUserDetails.loadUserByUsername(authentication.getName());
        var usersInfo = usersRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String checkToken = redisTemplate.opsForValue().get(usersInfo.getEmail());
        if (!hasText(checkToken)) {
            // 수정 필요
            throw new RuntimeException("test1");
        }

        return UsersInfoResponseVo.builder()
                .email(usersInfo.getEmail())
                .roleName(usersInfo.getRoles().getName().toString())
                .build();
    }


    private void handleMissingRefreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && hasText(authentication.getName())) {
            log.info("Authenticated user: " + authentication.getName());
            tokenProvider.searchAccessTokenByEmail(authentication);
        } else {
            log.warn("No authenticated user found in the security context.");
        }
    }

}
