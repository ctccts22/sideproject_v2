package v2.sideproject.store.user_management_service.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import v2.sideproject.store.user_management_service.adapter.in.web.request.LoginUsersRequest;

public interface LoginUsersUseCase {
    void loginByRequestInfo(LoginUsersRequest command, HttpServletRequest request, HttpServletResponse response);
}
