package v2.sideproject.store.user.service;

import org.springframework.data.domain.Pageable;
import v2.sideproject.store.redis.config.RestPage;
import v2.sideproject.store.user.dto.request.UsersRegisterRequestDto;
import v2.sideproject.store.user.dto.response.UsersRegisterResponseDto;
import v2.sideproject.store.user.dto.search.UsersSearchParamsDto;

public interface UsersService {
    void createUsers(UsersRegisterRequestDto usersRegisterRequestDto);

    RestPage<UsersRegisterResponseDto> fetchAllUsersDetails(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable);
}
