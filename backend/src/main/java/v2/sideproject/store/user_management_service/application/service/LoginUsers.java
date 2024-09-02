package v2.sideproject.store.user_management_service.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.common.annotation.UseCase;
import v2.sideproject.store.user.constants.AuthConstants;
import v2.sideproject.store.user.models.dto.TokensDto;
import v2.sideproject.store.user_management_service.adapter.in.web.request.LoginUsersRequest;
import v2.sideproject.store.user_management_service.application.port.in.LoginUsersUseCase;
import v2.sideproject.store.user_management_service.security.jwt.JwtTokenProvider;

@UseCase
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LoginUsers implements LoginUsersUseCase {
    private static final long maxAgeForCookie = 24 * 60 * 60;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void loginByRequestInfo(LoginUsersRequest command, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = null;
            String refreshToken = null;

            // Retrieve existing tokens
            TokensDto tokens = jwtTokenProvider.getAccessToken(request);
            accessToken = tokens.getAccessToken();
            refreshToken = tokens.getRefreshToken();

            // If access token is expired or null
            if (accessToken == null || !jwtTokenProvider.validateToken(accessToken)) {
                if (refreshToken != null) {
                    jwtTokenProvider.deleteRefreshTokenCookie(response, "refreshToken");
                    jwtTokenProvider.deleteRefreshToken(refreshToken);
                }
                // Create new tokens
                accessToken = jwtTokenProvider.createAccessToken(authentication);
                refreshToken = jwtTokenProvider.createRefreshToken(accessToken);
                jwtTokenProvider.createRefreshTokenCookie(response, "refreshToken", refreshToken, maxAgeForCookie);
            }

            // Add the new access token to the response header
            response.addHeader("Authorization", "Bearer " + accessToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(AuthConstants.MESSAGE_401);
        }
    }

}
