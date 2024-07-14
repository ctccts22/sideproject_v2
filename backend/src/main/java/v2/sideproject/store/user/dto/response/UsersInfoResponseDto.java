package v2.sideproject.store.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import v2.sideproject.store.user.enums.RolesName;

@Data
@AllArgsConstructor
@Builder
public class UsersInfoResponseDto {

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotNull(message = "roleName cannot be null or empty")
    @Schema(description = "User's roleName", example = "ADMIN")
    private RolesName roleName;
}
