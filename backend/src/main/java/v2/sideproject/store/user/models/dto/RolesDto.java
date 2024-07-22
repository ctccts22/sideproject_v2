package v2.sideproject.store.user.models.dto;

import lombok.*;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.enums.RolesName;

import java.io.Serializable;

/**
 * DTO for {@link Roles}
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RolesDto implements Serializable {
    private String roleId;
    private RolesName name;
}