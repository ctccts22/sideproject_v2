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
public class EmailVerificationRequest {
    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "New email to be verified", example = "newemail@test.com")
    private String newEmail;
}
