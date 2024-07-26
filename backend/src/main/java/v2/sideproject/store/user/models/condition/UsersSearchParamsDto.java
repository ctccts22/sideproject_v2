package v2.sideproject.store.user.models.condition;

import lombok.*;
import v2.sideproject.store.user.models.enums.Gender;
import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.models.enums.UsersStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsersSearchParamsDto {
    private String email;
    private String name;
    private String birth;
    private Gender gender;
    private UsersStatus status;
    private RolesName rolesName;
}
