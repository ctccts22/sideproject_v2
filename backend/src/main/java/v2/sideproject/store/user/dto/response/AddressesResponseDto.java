package v2.sideproject.store.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Schema(
        name = "AddressResponse",
        description = "Schema to hold successful response Users Address information"
)
@Getter
@Builder
@AllArgsConstructor
public class AddressesResponseDto {
    @NotEmpty(message = "mainAddress cannot be null or empty")
    @Schema(description = "User's mainAddress", example = "봉천동")
    private String mainAddress;
    @NotEmpty(message = "subAddress cannot be null or empty")
    @Schema(description = "User's subAddress", example = "104동")
    private String subAddress;
    @NotEmpty(message = "phone cannot be null or empty")
    @Schema(description = "User's phone", example = "010-1111-1111")
    private String phone;
    @NotEmpty(message = "zipCode cannot be null or empty")
    @Schema(description = "User's zipCode", example = "90045")
    private String zipCode;
    @NotNull(message = "userId is fk from users table ")
    @Schema(description = "User's id", example = "90045")
    private Long userId;
}
