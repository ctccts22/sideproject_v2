package v2.sideproject.store.user.models.dto;

import lombok.*;
import v2.sideproject.store.user.enums.AddressesType;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddressesDto implements Serializable {
    private Long addressesId;
    private UsersDto usersDto;
    private String mainAddress;
    private String subAddress;
    private String zipCode;
    private String phone;
    private AddressesType addressesType;
}