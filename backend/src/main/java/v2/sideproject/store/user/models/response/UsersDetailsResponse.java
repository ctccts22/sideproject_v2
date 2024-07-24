package v2.sideproject.store.user.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.models.dto.AddressesDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UsersDetailsResponse {

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotEmpty(message = "Name cannot be null or empty")
    @Schema(description = "User's name", example = "John Doe")
    private String name;

    @NotEmpty(message = "Name cannot be null or empty")
    @Schema(description = "User's birth", example = "950315")
    private String birth;

    @NotNull(message = "Status cannot be null")
    @Schema(description = "User's status", example = "ACTIVE")
    private UsersStatus status;

    @NotNull(message = "Name cannot be null or empty")
    @Schema(description = "User's gender", example = "MALE")
    private Gender gender;

    @NotNull(message = "Status cannot be null")
    @Schema(description = "User's mobileCarrier", example = "KT")
    private MobileCarrier mobileCarrier;

    @NotEmpty(message = "Cellphone number cannot be null or empty")
    @Schema(description = "User's phone number", example = "123-456-7890")
    private String phone;

    @NotNull(message = "Status cannot be null")
    @Schema(description = "RoleName associated with the user", example = "ADMIN")
    private RolesName roleName;
}
