package v2.sideproject.store.user.service;

import org.springframework.data.domain.Page;
import v2.sideproject.store.user.vo.request.UsersDetailsRequestVo;
import v2.sideproject.store.user.vo.response.UsersDetailsResponseVo;

public interface UsersService {
    void createUsers(UsersDetailsRequestVo usersDetailsRequestVo);

    Page<UsersDetailsResponseVo> fetchAllUsersDetails();

}
