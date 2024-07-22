package v2.sideproject.store.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.user.models.condition.UsersOrderCondition;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.entity.Users;

import java.util.Optional;

public interface UsersRepositoryCustom {


    Optional<Users> findByEmailWithRole(String email);

    Page<UsersDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable, UsersOrderCondition usersOrderCondition);
}
