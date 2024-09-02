package v2.sideproject.store.user_management_service.application.port.out;

import v2.sideproject.store.user_management_service.adapter.in.web.response.RefreshUsersResponse;
import v2.sideproject.store.user_management_service.domain.Users;

public interface RefreshUsersPort {
    Users getUserInfo(RefreshUsersResponse refreshUsersResponse);
}
