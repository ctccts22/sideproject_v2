package v2.sideproject.store.user.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import v2.sideproject.store.user.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);


}
