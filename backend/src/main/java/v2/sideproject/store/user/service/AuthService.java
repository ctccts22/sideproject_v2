package v2.sideproject.store.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import v2.sideproject.store.user.models.vo.request.UsersLoginRequest;
import v2.sideproject.store.user.models.vo.response.UsersInfoResponse;

public interface AuthService {
    void login(UsersLoginRequest usersLoginRequest, HttpServletResponse response) throws BadRequestException;
    void logout(HttpServletRequest request, HttpServletResponse response);
    void getAccessToken(HttpServletRequest request, HttpServletResponse response);


    UsersInfoResponse getUserInfo();

}
