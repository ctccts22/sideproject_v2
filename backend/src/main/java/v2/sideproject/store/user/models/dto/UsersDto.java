package v2.sideproject.store.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.UsersStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UsersDto {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String birth;
    private Gender gender;
    private UsersStatus status;
    private MobileCarrier mobileCarrier;
    private String phone;
    private RolesDto roles;
    private List<AddressesDto> addressesList = new ArrayList<>();
}
