package v2.sideproject.store.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import v2.sideproject.store.user.entity.Addresses;

public interface AddressesRepository extends JpaRepository<Addresses, Long> {
}
