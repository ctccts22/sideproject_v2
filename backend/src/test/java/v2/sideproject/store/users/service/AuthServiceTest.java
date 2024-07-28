package v2.sideproject.store.users.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.enums.Gender;
import v2.sideproject.store.user.models.enums.MobileCarrier;
import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.models.enums.UsersStatus;
import v2.sideproject.store.user.models.vo.request.UsersLoginRequest;
import v2.sideproject.store.user.models.vo.response.UsersInfoResponse;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.impl.AuthServiceImpl;
import v2.sideproject.store.user.userDetails.CustomUserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    @DisplayName("JUnit test for login method")
    void givenLoginRequestAndResponse_whenCreateToken_thenLoginSuccess() {
        UsersLoginRequest usersLoginRequest = UsersLoginRequest.builder()
                .email("test@test.com")
                .password("test")
                .build();

        UsersDto mockUser = new UsersDto();
        Optional<UsersDto> optionalUser = Optional.of(mockUser);
        given(usersRepository.findByEmail(usersLoginRequest.getEmail())).willReturn(optionalUser);

        Authentication authentication = mock(Authentication.class);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        given(jwtTokenProvider.createAccessToken(authentication)).willReturn(accessToken);
        given(jwtTokenProvider.createRefreshToken(authentication)).willReturn(refreshToken);
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
    @DisplayName("JUnit test for logout method")
    void givenLogoutMethod_whenGetUserEmail_thenReturnDeleteTokens() {
        // given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        UserDetails userDetails = mock(UserDetails.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getName()).willReturn("testUser@test.com");
        given(customUserDetails.loadUserByUsername("testUser@test.com")).willReturn(userDetails);
        given(userDetails.getUsername()).willReturn("testUser@test.com");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie refreshTokenCookie = new Cookie("refreshToken", "testRefreshToken");
        given(request.getCookies()).willReturn(new Cookie[]{refreshTokenCookie});

        doNothing().when(jwtTokenProvider).deleteRefreshTokenCookie(response, "refreshToken");

        // when
        authService.logout(request, response);

        // then
        verify(jwtTokenProvider).deleteRefreshTokenCookie(response, "refreshToken");
        verify(redisTemplate).delete("testRefreshToken");
        verify(redisTemplate).delete("testUser@test.com");
    }

    @Test
    @DisplayName("JUnit test for getToken method")
    void givenRequest_whenGetAccessToken_thenReturnAccessToken() {
        // given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = mock(UserDetails.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);

        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getName()).willReturn("testUser@test.com");
        given(customUserDetails.loadUserByUsername("testUser@test.com")).willReturn(userDetails);
        given(userDetails.getUsername()).willReturn("testUser@test.com");

        UsersDto usersDto = UsersDto.builder().email(userDetails.getUsername()).build();
        given(usersRepository.findByEmail("testUser@test.com")).willReturn(Optional.of(usersDto));
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get("testUser@test.com")).willReturn("testToken");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie refreshTokenCookie = new Cookie("refreshToken", "testRefreshToken");
        given(request.getCookies()).willReturn(new Cookie[]{refreshTokenCookie});

        given(jwtTokenProvider.searchAccessTokenByRefreshToken("testRefreshToken")).willReturn("accessToken");

        // when
        authService.getAccessToken(request, response);

        // verify
        verify(usersRepository).findByEmail("testUser@test.com");
        verify(jwtTokenProvider).searchAccessTokenByRefreshToken("testRefreshToken");
        verify(response).setHeader("Authorization", "Bearer accessToken");
    }


    @Test
    @DisplayName("JUnit test for getUserInfo method")
    void givenAuthenticatedUser_whenGetUserInfo_thenReturnUserInfo() {
        // given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = mock(UserDetails.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);

        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getName()).willReturn("testUser@test.com");
        given(customUserDetails.loadUserByUsername("testUser@test.com")).willReturn(userDetails);
        given(userDetails.getUsername()).willReturn("testUser@test.com");

        RolesDto roles = RolesDto.builder().name(RolesName.CUSTOMER).build();
        UsersDto usersDto =
                UsersDto.builder()
                        .email(userDetails.getUsername())
                        .roles(RolesDto.builder()
                                .name(roles.getName())
                                .build()).build();
        given(usersRepository.findByEmailWithRole("testUser@test.com")).willReturn(Optional.of(usersDto));
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get("testUser@test.com")).willReturn("testToken");

        // when
        UsersInfoResponse usersInfoResponse = authService.getUserInfo();

        // verify
        verify(usersRepository).findByEmailWithRole("testUser@test.com");
        verify(redisTemplate.opsForValue()).get("testUser@test.com");

        // then
        assertEquals("testUser@test.com", usersInfoResponse.getEmail());
        assertEquals(RolesName.CUSTOMER, usersInfoResponse.getRoleName());
    }

    @Test
    @DisplayName("JUnit test for getUserInfo and cause exception method")
    void givenNoTokenInRedis_whenGetUserInfo_thenThrowException() {
        // given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        UserDetails userDetails = mock(UserDetails.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);

        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getName()).willReturn("testUser@test.com");
        given(customUserDetails.loadUserByUsername("testUser@test.com")).willReturn(userDetails);
        given(userDetails.getUsername()).willReturn("testUser@test.com");

        RolesDto roles = RolesDto.builder().name(RolesName.CUSTOMER).build();
        UsersDto usersDto =
                UsersDto.builder()
                        .email(userDetails.getUsername())
                        .roles(RolesDto.builder()
                                .name(roles.getName())
                                .build()).build();
        given(usersRepository.findByEmailWithRole("testUser@test.com")).willReturn(Optional.of(usersDto));
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.get("testUser@test.com")).willReturn(null);

        // assert exception
        assertThrows(RuntimeException.class, () -> authService.getUserInfo());

        // verify
        verify(usersRepository).findByEmailWithRole("testUser@test.com");
        verify(redisTemplate.opsForValue()).get("testUser@test.com");
    }
}
