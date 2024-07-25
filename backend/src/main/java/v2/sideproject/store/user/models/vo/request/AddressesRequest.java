package v2.sideproject.store.user.models.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import v2.sideproject.store.user.models.enums.AddressesType;

@Schema(
        name = "AddressRequest",
        description = "Schema to hold successful response Users Address information"
)
@Getter
@Builder
@AllArgsConstructor
public class AddressesRequest {
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
    @NotEmpty(message = "addressesType cannot be null or empty")
    @Schema(description = "User's addressesType", example = "HOME")
    private AddressesType addressesType;
}
