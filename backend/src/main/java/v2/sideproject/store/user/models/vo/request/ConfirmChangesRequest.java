package v2.sideproject.store.user.models.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmChangesRequest {
    @NotEmpty(message = "Verification code cannot be null or empty")
    @Schema(description = "Verification code sent to new email", example = "abc123")
    private String verificationCode;
}