package v2.sideproject.store.user_management_service.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import v2.sideproject.store.common.annotation.PersistenceAdapter;
import v2.sideproject.store.user_management_service.adapter.in.web.response.RefreshUsersResponse;
import v2.sideproject.store.user_management_service.application.port.out.LoginUsersPort;
import v2.sideproject.store.user_management_service.application.port.out.RefreshUsersPort;
import v2.sideproject.store.user_management_service.application.port.out.RegisterUsersPort;

@RequiredArgsConstructor
@PersistenceAdapter
public class UsersPersistenceAdapter implements RegisterUsersPort, LoginUsersPort, RefreshUsersPort {
    private final DSLContext dsl;


    @Override
    public RefreshUsersResponse findUserInfo(RefreshUsersResponse refreshUsersResponse) {
        return refreshUsersResponse;
    }
}
