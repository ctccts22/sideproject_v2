package v2.sideproject.store.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import v2.sideproject.store.user.dto.request.UsersLoginRequestDto;
import v2.sideproject.store.user.dto.response.UsersInfoResponseDto;

public interface AuthService {
    void login(UsersLoginRequestDto usersLoginRequestDto, HttpServletResponse response) throws BadRequestException;
    void logout(HttpServletRequest request, HttpServletResponse response);
    void getAccessToken(HttpServletRequest request, HttpServletResponse response);


    UsersInfoResponseDto getUserInfo();

}
