package v2.sideproject.store.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import v2.sideproject.store.user.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long>,UsersRepositoryCustom,QuerydslPredicateExecutor {
    Optional<Users> findByCellphone(String cellphone);

    Optional<Users> findByEmail(String email);


}
