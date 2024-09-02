package v2.sideproject.store.user_management_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import v2.sideproject.store.user.models.enums.RolesName;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    private Long roleId;
    private RolesName roleName;
}
