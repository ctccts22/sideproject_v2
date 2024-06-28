package v2.sideproject.store.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import v2.sideproject.store.user.enums.UsersStatus;

@Getter
@Builder
public class UsersDto {

    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User's password", example = "password123")
    private String password;

    @NotEmpty(message = "Name cannot be null or empty")
    @Schema(description = "User's name", example = "John Doe")
    private String name;

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

    @Schema(description = "Role ID associated with the user", example = "2")
    private String roleId;

    @Schema(description = "Company ID associated with the user", example = "3")
    private Long companyId;
}
