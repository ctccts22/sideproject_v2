package v2.sideproject.store.user_management_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.common.annotation.UseCase;
import v2.sideproject.store.user_management_service.application.port.in.RegisterUsersUseCase;
import v2.sideproject.store.user_management_service.application.port.out.RegisterUsersPort;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterUsers implements RegisterUsersUseCase {
    private final RegisterUsersPort registerUsersPort;
}
