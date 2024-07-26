package v2.sideproject.store.user.models.dto;

import lombok.*;
import v2.sideproject.store.user.models.enums.RolesName;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class RolesDto {
    private String roleId;
    private RolesName name;
}