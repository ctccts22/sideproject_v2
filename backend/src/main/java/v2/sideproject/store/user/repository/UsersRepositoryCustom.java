package v2.sideproject.store.user.repository;

import v2.sideproject.store.user.entity.Users;

import java.util.Optional;

public interface UsersRepositoryCustom {


    Optional<Users> findByEmailWithRole(String email);
}
