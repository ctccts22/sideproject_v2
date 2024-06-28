package v2.sideproject.store.user.vo;

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
        name = "Response",
        description = "Schema to hold successful response Users Detail information"
)
@Data
@AllArgsConstructor
@Builder
public class UsersDetailsResponseVo {

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotEmpty(message = "Name cannot be null or empty")
    @Schema(description = "User's name", example = "John Doe")
    private String name;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User's password", example = "password123")
    private String password;

    @NotNull(message = "Status cannot be null")
    @Schema(description = "User's status", example = "ACTIVE")
    private UsersStatus status;

    @NotEmpty(message = "Department cannot be null or empty")
    @Schema(description = "User's department", example = "Sales")
    private String department;

    @NotEmpty(message = "Position cannot be null or empty")
    @Schema(description = "User's position", example = "Manager")
    private String position;

    @NotEmpty(message = "Cellphone number cannot be null or empty")
    @Schema(description = "User's cellphone number", example = "123-456-7890")
    private String cellphone;

    @Schema(description = "User's telephone number", example = "098-765-4321")
    private String telephone;

    // Role join
    @Schema(description = "RoleName associated with the user", example = "2")
    private RolesName roleName;

    // company join
    @Schema(description = "companyName associated with the user", example = "3")
    private String companyName;

    @Schema(description = "parentCompany associated with the user", example = "3")
    private String parentCompany;

    @Schema(description = "address associated with the user", example = "3")
    private String address;
}
