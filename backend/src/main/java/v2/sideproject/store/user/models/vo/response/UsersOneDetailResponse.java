package v2.sideproject.store.user.models.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import v2.sideproject.store.user.models.enums.MobileCarrier;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersOneDetailResponse {

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotEmpty(message = "Name cannot be null or empty")
    @Schema(description = "User's name", example = "John Doe")
    private String name;

    @NotNull(message = "Status cannot be null")
    @Schema(description = "User's mobileCarrier", example = "KT")
    private MobileCarrier mobileCarrier;

    @NotEmpty(message = "Cellphone number cannot be null or empty")
    @Schema(description = "User's phone number", example = "123-456-7890")
    private String phone;
}
