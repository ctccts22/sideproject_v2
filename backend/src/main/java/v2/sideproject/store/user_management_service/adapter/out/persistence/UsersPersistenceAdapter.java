package v2.sideproject.store.user_management_service.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import v2.sideproject.store.common.annotation.PersistenceAdapter;
import v2.sideproject.store.user_management_service.application.port.out.LoginUsersPort;
import v2.sideproject.store.user_management_service.application.port.out.RefreshUsersPort;
import v2.sideproject.store.user_management_service.application.port.out.RegisterUsersPort;
import v2.sideproject.store.user_management_service.domain.Users;

@RequiredArgsConstructor
@PersistenceAdapter
public class UsersPersistenceAdapter implements RegisterUsersPort, LoginUsersPort, RefreshUsersPort {
    private final DSLContext dsl;


    @Override
    public Users getUserInfo(Users users) {
        return null;
    }
}
