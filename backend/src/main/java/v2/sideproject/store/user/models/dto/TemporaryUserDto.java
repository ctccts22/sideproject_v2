package v2.sideproject.store.user.models.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import v2.sideproject.store.user.models.enums.MobileCarrier;
import v2.sideproject.store.user.models.enums.UpdateStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class TemporaryUserDto {
    @Email
    private String email;
    private String name;
    private MobileCarrier mobileCarrier;
    private String phone;
    private String verificationCode;
    private UpdateStatus updateStatus;
}
