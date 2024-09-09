package v2.sideproject.store.user_management_service.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.common.annotation.UseCase;
import v2.sideproject.store.user.models.dto.TokensDto;
import v2.sideproject.store.user_management_service.adapter.in.web.response.RefreshUsersResponse;
import v2.sideproject.store.user_management_service.application.port.in.RefreshUsersUseCase;
import v2.sideproject.store.user_management_service.application.port.out.RefreshUsersPort;
import v2.sideproject.store.user_management_service.domain.Users;
import v2.sideproject.store.user_management_service.security.jwt.JwtTokenProvider;

@UseCase
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RefreshUsers implements RefreshUsersUseCase {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshUsersPort refreshUsersPort;

    @Override
    public RefreshUsersResponse getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        TokensDto tokens = jwtTokenProvider.getAccessToken(request);
        String accessToken = tokens.getAccessToken();

        Users users = Users.builder()
                .email(jwtTokenProvider.getEmailFromToken(accessToken))
//                .role(jwtTokenProvider.getRoleFromToken(accessToken))
                .build();

        response.addHeader("Authorization", "Bearer " + accessToken);

        RefreshUsersResponse refreshUsersResponse = RefreshUsersResponse.builder()
                .email(users.getEmail())
                .build();


        return refreshUsersPort.findUserInfo(refreshUsersResponse);
    }
}
