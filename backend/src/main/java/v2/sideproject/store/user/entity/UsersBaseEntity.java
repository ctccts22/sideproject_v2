package v2.sideproject.store.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UsersBaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = true, updatable = false)
//    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true, insertable = false)
//    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime updatedAt;

    @LastModifiedDate
    @Column(name = "deleted_at", nullable = true, insertable = false)
//    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime deletedAt;
}