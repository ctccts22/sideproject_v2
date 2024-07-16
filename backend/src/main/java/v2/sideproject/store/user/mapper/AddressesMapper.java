package v2.sideproject.store.user.mapper;

import v2.sideproject.store.user.dto.response.AddressesResponseDto;
import v2.sideproject.store.user.entity.Addresses;
import v2.sideproject.store.user.entity.Users;

public class AddressesMapper {

    public static Addresses mapToAddresses(AddressesResponseDto addressesResponseDto) {
            Addresses addresses = Addresses.builder()
                    .users(Users.builder().userId(addressesResponseDto.getUserId()).build())
                    .mainAddress(addressesResponseDto.getMainAddress())
                    .subAddress(addressesResponseDto.getSubAddress())
                    .zipCode(addressesResponseDto.getZipCode())
                    .phone(addressesResponseDto.getPhone())
                    .build();
            return addresses;
        }

}
