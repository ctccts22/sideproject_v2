package v2.sideproject.store.users.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import v2.sideproject.store.jwt.JwtTokenProvider;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.enums.Gender;
import v2.sideproject.store.user.models.enums.MobileCarrier;
import v2.sideproject.store.user.models.enums.UsersStatus;
import v2.sideproject.store.user.models.vo.request.UsersLoginRequest;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.impl.AuthServiceImpl;
import v2.sideproject.store.user.userDetails.CustomUserDetails;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomUserDetails customUserDetails;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setup() {

    }

    @Test
    void givenLoginRequestAndResponse_whenCreateToken_thenLoginSuccess() {
        UsersLoginRequest usersLoginRequest = UsersLoginRequest.builder()
                .email("test@test.com")
                .password("test")
                .build();

        UsersDto mockUser = new UsersDto();
        Optional<UsersDto> optionalUser = Optional.of(mockUser);

        when(usersRepository.findByEmail(usersLoginRequest.getEmail())).thenReturn(optionalUser);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        when(jwtTokenProvider.createAccessToken(authentication)).thenReturn(accessToken);
        when(jwtTokenProvider.createRefreshToken(authentication)).thenReturn(refreshToken);

        HttpServletResponse response = mock(HttpServletResponse.class);

        // when
        authService.login(usersLoginRequest, response);

        // then
        verify(usersRepository).findByEmail(usersLoginRequest.getEmail());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).createAccessToken(authentication);
        verify(jwtTokenProvider).createRefreshToken(authentication);
        verify(response).addHeader("Authorization", "Bearer " + accessToken);
    }

    @Test
    void logout_shouldDeleteTokens() {
        // given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        UserDetails userDetails = mock(UserDetails.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser@test.com\"");
        when(customUserDetails.loadUserByUsername("testUser@test.com\"")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser@test.com\"");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie refreshTokenCookie = new Cookie("refreshToken", "testRefreshToken");
        when(request.getCookies()).thenReturn(new Cookie[]{refreshTokenCookie});

        doNothing().when(jwtTokenProvider).deleteRefreshTokenCookie(response, "refreshToken");

        // when
        authService.logout(request, response);

        // then
        verify(jwtTokenProvider).deleteRefreshTokenCookie(response, "refreshToken");
        verify(redisTemplate).delete("testRefreshToken");
        verify(redisTemplate).delete("testUser@test.com\"");
    }
}
