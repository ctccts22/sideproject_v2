package v2.sideproject.store.user.models.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import v2.sideproject.store.user.models.enums.Gender;
import v2.sideproject.store.user.models.enums.MobileCarrier;
import v2.sideproject.store.user.models.enums.UsersStatus;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UsersDto {
//    public class UsersDto implements Serializable {
    /**
     * In a Spring boot, I really don't need to implement Serializable for my DTOs
     * Cuz, Jackson handles JSON serializable and deserializable automatically throw @RequestBody.
     */
    private Long userId;
    @Email
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
    // @ManyToOne
    private RolesDto roles;
    // @OneToMany
    private List<AddressesDto> addressesList;

}
