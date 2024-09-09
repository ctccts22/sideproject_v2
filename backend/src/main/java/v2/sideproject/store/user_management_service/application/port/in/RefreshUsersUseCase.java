package v2.sideproject.store.user_management_service.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import v2.sideproject.store.user_management_service.adapter.in.web.response.RefreshUsersResponse;

public interface RefreshUsersUseCase {
    RefreshUsersResponse getUserInfo(HttpServletRequest request, HttpServletResponse response);
}
