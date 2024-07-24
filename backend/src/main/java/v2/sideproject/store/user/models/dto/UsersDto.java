package v2.sideproject.store.user.models.dto;

import lombok.*;
import org.jooq.RecordMapper;
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
@ToString
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

    public UsersDto(String email, String name, String birth) {
        this.email = email;
        this.name = name;
        this.birth = birth;
    }
}
