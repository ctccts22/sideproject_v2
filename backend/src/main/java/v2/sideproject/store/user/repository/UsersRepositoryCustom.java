package v2.sideproject.store.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.user.dto.response.UsersRegisterResponseDto;
import v2.sideproject.store.user.dto.search.UsersSearchParamsDto;
import v2.sideproject.store.user.entity.Users;

import java.util.Optional;

public interface UsersRepositoryCustom {


    Optional<Users> findByEmailWithRole(String email);

    Page<UsersRegisterResponseDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable);
}
