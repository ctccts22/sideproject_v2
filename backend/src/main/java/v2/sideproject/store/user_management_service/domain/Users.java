package v2.sideproject.store.user_management_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import v2.sideproject.store.user.models.enums.Gender;
import v2.sideproject.store.user.models.enums.MobileCarrier;
import v2.sideproject.store.user.models.enums.UsersStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Long userId;
    private String username;
    private String email;
    private String hashedPassword;
    private String name;
    private String dob;
    private Gender gender;
    private UsersStatus status;
    private MobileCarrier mobileCarrier;
    private String phone;
    private String createdAt;
    private String updatedAt;
    // @OneToMany
    private List<Roles> rolesList;
    // @OneToMany
    private List<Addresses> addressesList;
}
