package v2.sideproject.store.user.repository.jooq;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.dto.UsersDto;

import java.util.Optional;

public interface UsersRepositoryCustom {


    Optional<UsersDto> findByEmailWithRole(String email);

    Page<UsersDto> findAllUsersDetailsByParams(UsersSearchParamsDto usersSearchParamsDto, Pageable pageable);

}
