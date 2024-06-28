package v2.sideproject.store.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// For JPA test
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class JpaAuditingConfiguration {
}