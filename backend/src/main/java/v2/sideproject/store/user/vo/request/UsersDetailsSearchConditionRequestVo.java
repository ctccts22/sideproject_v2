package v2.sideproject.store.user.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;

@Schema(
        name = "Request Search Condition",
        description = "Schema to hold successful Request Search Condition information"
)
@Data
@AllArgsConstructor
@Builder
public class UsersDetailsSearchConditionRequestVo {

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotEmpty(message = "Name cannot be null or empty")
    @Schema(description = "User's name", example = "John Doe")
    private String name;

    @NotNull(message = "Status cannot be null")
    @Schema(description = "User's status", example = "ACTIVE")
    private UsersStatus status;

    @NotEmpty(message = "Department cannot be null or empty")
    @Schema(description = "User's department", example = "Sales")
    private String department;

    @Schema(description = "RoleName associated with the user", example = "2")
    private RolesName roleName;

}
