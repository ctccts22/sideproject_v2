package v2.sideproject.store.user.models.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import v2.sideproject.store.user.models.enums.MobileCarrier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserInfoRequest {
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
