package v2.sideproject.store.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.UsersStatus;

@Schema(
        name = "UsersDetailsRequest",
        description = "Schema to hold successful response Users Detail information"
)
@Getter
@Builder
@AllArgsConstructor
public class UsersDetailsRequestDto {

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Schema(description = "Email is used for login", example = "test@test.com")
    private String email;

    @NotEmpty(message = "Name cannot be null or empty")
    @Schema(description = "User's name", example = "John Doe")
    private String name;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User's password", example = "password123")
    private String password;

    @NotEmpty(message = "Password cannot be null or empty")
    @Schema(description = "User's password", example = "password123")
    private String checkPassword;

    @NotEmpty(message = "Name cannot be null or empty")
    @Schema(description = "User's birth", example = "950315")
    private String birth;

    @NotNull(message = "Name cannot be null or empty")
    @Schema(description = "User's gender", example = "MALE")
    private Gender gender;

    @NotNull(message = "Status cannot be null")
    @Schema(description = "User's status", example = "APPROVED")
    private UsersStatus status;

    @NotNull(message = "Status cannot be null")
    @Schema(description = "User's mobileCarrier", example = "KT")
    private MobileCarrier mobileCarrier;

    @NotEmpty(message = "Cellphone number cannot be null or empty")
    @Schema(description = "User's phone number", example = "123-456-7890")
    private String phone;
}
