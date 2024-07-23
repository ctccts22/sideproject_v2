package v2.sideproject.store.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.models.condition.UsersOrderCondition;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.dto.UsersDto;

import java.util.Optional;

public interface UsersRepositoryCustoms {


    Optional<UsersDto> findByEmailWithRole(String email);

}
