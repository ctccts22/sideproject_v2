package v2.sideproject.store.user.repository;

import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.models.dto.RolesDto;

import java.util.Optional;

public interface RolesRepository {
    Optional<RolesDto> findByName(RolesName rolesName);
}
