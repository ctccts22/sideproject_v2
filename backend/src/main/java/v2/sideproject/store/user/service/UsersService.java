package v2.sideproject.store.user.service;

import org.springframework.data.domain.Pageable;
import v2.sideproject.store.common.db.redis.utils.RestPage;
import v2.sideproject.store.user.models.vo.request.UsersRegisterRequest;
import v2.sideproject.store.user.models.vo.response.UsersDetailsResponse;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.vo.response.UsersOneDetailResponse;

public interface UsersService {
    void createUsers(UsersRegisterRequest usersRegisterRequest);

    RestPage<UsersDetailsResponse> fetchAllUsersDetails(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable);

    UsersOneDetailResponse getOneUserInfo();

}
