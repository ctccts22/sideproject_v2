package v2.sideproject.store.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import v2.sideproject.store.company.entity.Companies;

import java.util.Optional;

public interface CompaniesRepository extends JpaRepository<Companies, Long> {

    Optional<Companies> findByName(String companyName);

}
