package v2.sideproject.store.user.models.dto;

import lombok.*;
import v2.sideproject.store.user.models.enums.AddressesType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AddressesDto {
    private Long addressesId;
    private UsersDto usersDto;
    private String mainAddress;
    private String subAddress;
    private String zipCode;
    private AddressesType addressesType;
}