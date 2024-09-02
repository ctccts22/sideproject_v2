package v2.sideproject.store.user_management_service.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import v2.sideproject.store.user.userDetails.CustomUserDetails;
import v2.sideproject.store.user_management_service.security.jwt.JwtTokenProvider;
import v2.sideproject.store.utils.CookieUtil;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetails customUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtTokenProvider.getAccessTokenOnHeader(request);

        try {
            if (hasText(token) && !jwtTokenProvider.validateToken(token)) {
                // expired Token -> logout
                jwtTokenProvider.deleteRefreshTokenCookie(response, "refreshToken");
                Cookie refreshTokenCookie = CookieUtil.getCookie(request, "refreshToken");

                if (refreshTokenCookie != null) {
                    String refreshToken = refreshTokenCookie.getValue();
                    jwtTokenProvider.deleteRefreshToken(refreshToken);
                }
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired JWT token");
                return;
            }

            if (hasText(token) && jwtTokenProvider.validateToken(token)) {
                String email = jwtTokenProvider.getEmailFromToken(token);
                UserDetails userDetails = customUserDetails.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("JWT authentication failed", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

