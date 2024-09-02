package v2.sideproject.store.user_management_service.application.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.common.annotation.UseCase;
import v2.sideproject.store.user.userDetails.CustomUserDetails;
import v2.sideproject.store.user_management_service.application.port.in.LogoutUsersUseCase;
import v2.sideproject.store.user_management_service.security.jwt.JwtTokenProvider;
import v2.sideproject.store.utils.CookieUtil;

@UseCase
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LogoutUsers implements LogoutUsersUseCase {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetails customUserDetails;

    @Override
    @Transactional
    public void logoutUsers(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = customUserDetails.loadUserByUsername(authentication.getName());
        Cookie refreshTokenCookie = CookieUtil.getCookie(request, "refreshToken");
        if (refreshTokenCookie != null) {
            // refreshToken delete
            jwtTokenProvider.deleteRefreshTokenCookie(response, "refreshToken");
            jwtTokenProvider.deleteRefreshToken(refreshTokenCookie.getValue());
        }
    }
}
