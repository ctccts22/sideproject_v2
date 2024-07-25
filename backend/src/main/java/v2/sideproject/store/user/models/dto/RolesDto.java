package v2.sideproject.store.user.models.dto;

import lombok.*;
import v2.sideproject.store.user.enums.RolesName;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RolesDto implements Serializable {
    private String roleId;
    private RolesName name;
}