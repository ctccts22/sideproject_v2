package v2.sideproject.store.user.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.jwt.JwtTokenProvider;
import v2.sideproject.store.user.constants.AuthConstants;
import v2.sideproject.store.user.models.vo.request.UsersLoginRequest;
import v2.sideproject.store.user.models.vo.response.UsersInfoResponse;
import v2.sideproject.store.user.service.AuthService;
import v2.sideproject.store.user.userDetails.CustomUserDetails;
import v2.sideproject.store.utils.CookieUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private static final long maxAgeForCookie = 24 * 60 * 60;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final CustomUserDetails customUserDetails;


    @Override
    @Transactional
    public void login(UsersLoginRequest usersLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usersLoginRequest.getEmail(), usersLoginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = getAccessToken(request);
            String refreshToken;

            if (accessToken == null) {
                accessToken = jwtTokenProvider.createAccessToken(authentication);
                refreshToken = jwtTokenProvider.createRefreshToken(accessToken);

                jwtTokenProvider.createRefreshTokenCookie(response, "refreshToken", refreshToken, maxAgeForCookie);
            }

            response.addHeader("Authorization", "Bearer " + accessToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(AuthConstants.MESSAGE_401);
        }
    }



    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = customUserDetails.loadUserByUsername(authentication.getName());
        Cookie refreshTokenCookie = CookieUtil.getCookie(request, "refreshToken");
        if (refreshTokenCookie != null) {
            // refreshToken delete
            jwtTokenProvider.deleteRefreshTokenCookie(response, "refreshToken");
            redisTemplate.delete(refreshTokenCookie.getValue());
        }
    }

    @Override
    public UsersInfoResponse getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = getAccessToken(request);
        UsersInfoResponse usersInfoResponse = UsersInfoResponse.builder()
                .email(jwtTokenProvider.getEmailFromToken(accessToken))
                .role(jwtTokenProvider.getRoleFromToken(accessToken))
                .build();

        response.addHeader("Authorization", "Bearer " + accessToken);
        return usersInfoResponse;
    }

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = null;
        String refreshToken;

        // Check for existing refresh token in cookies
        Cookie refreshTokenCookie = CookieUtil.getCookie(request, "refreshToken");

        if (refreshTokenCookie != null) {
            refreshToken = refreshTokenCookie.getValue();
            log.info("Refresh Token (from cookie): {}", refreshToken);

            // Try to get a new access token using the existing refresh token
            accessToken = jwtTokenProvider.getAccessTokenByRefreshToken(refreshToken);
            log.info("Access Token (generated): {}", accessToken);
        }
        return accessToken;
    }
}
