package v2.sideproject.store.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.vo.request.UsersRegisterRequest;

import java.util.Optional;

public interface UsersRepository {
    Long saveUsers(UsersRegisterRequest usersRegisterRequest, String encodedPassword, RolesDto rolesDto);

    Optional<UsersDto> findByEmail(String email);

    Optional<UsersDto> findByEmailWithRole(String email);

    Page<UsersDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable);

    int saveAddresses(UsersRegisterRequest usersRegisterRequest, Long userId);

}
