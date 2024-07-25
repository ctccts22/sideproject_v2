package v2.sideproject.store.user.models.dto;

import lombok.*;
import v2.sideproject.store.user.models.enums.Gender;
import v2.sideproject.store.user.models.enums.MobileCarrier;
import v2.sideproject.store.user.models.enums.UsersStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class UsersDto implements Serializable {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String birth;
    private Gender gender;
    private UsersStatus status;
    private MobileCarrier mobileCarrier;
    private String phone;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private RolesDto roles;
    private List<AddressesDto> addressesList = new ArrayList<>();

}
