package v2.sideproject.store.user.models.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import v2.sideproject.store.user.models.enums.RolesName;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersInfoResponse  {

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotNull(message = "roleName cannot be null or empty")
    @Schema(description = "User's roleName", example = "ADMIN")
    private RolesName roleName;
}
