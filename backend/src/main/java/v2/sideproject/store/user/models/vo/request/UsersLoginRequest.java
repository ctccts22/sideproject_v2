package v2.sideproject.store.user.models.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersLoginRequest {
    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User's password", example = "test")
    private String password;
}
