package v2.sideproject.store.user.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.enums.RolesName;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, String> {
    Optional<Roles> findByName(RolesName rolesName);
}
