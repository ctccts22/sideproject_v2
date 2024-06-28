package v2.sideproject.store.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import v2.sideproject.store.user.vo.request.UsersLoginRequestVo;
import v2.sideproject.store.user.vo.response.UsersInfoResponseVo;

public interface AuthService {
    void login(UsersLoginRequestVo usersLoginRequestVo, HttpServletResponse response) throws BadRequestException;
    void logout(HttpServletRequest request, HttpServletResponse response);
    void getAccessToken(HttpServletRequest request, HttpServletResponse response);


    UsersInfoResponseVo getUserInfo();

}
