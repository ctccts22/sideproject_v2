package v2.sideproject.store.user_management_service.application.port.out;

import v2.sideproject.store.user_management_service.adapter.in.web.response.RefreshUsersResponse;

public interface RefreshUsersPort {
    RefreshUsersResponse findUserInfo(RefreshUsersResponse refreshUsersResponse);
}
