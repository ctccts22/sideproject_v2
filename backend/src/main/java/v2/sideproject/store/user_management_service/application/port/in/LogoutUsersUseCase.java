package v2.sideproject.store.user_management_service.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LogoutUsersUseCase {
    void logoutUsers(HttpServletRequest request, HttpServletResponse response);
}
