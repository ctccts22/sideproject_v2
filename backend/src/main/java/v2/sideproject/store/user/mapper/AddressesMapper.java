package v2.sideproject.store.user.mapper;

import v2.sideproject.store.user.models.response.AddressesResponse;
import v2.sideproject.store.user.entity.Addresses;
import v2.sideproject.store.user.entity.Users;

public class AddressesMapper {

    public static Addresses mapToAddresses(AddressesResponse addressesResponse) {
            Addresses addresses = Addresses.builder()
                    .users(Users.builder().userId(addressesResponse.getUserId()).build())
                    .mainAddress(addressesResponse.getMainAddress())
                    .subAddress(addressesResponse.getSubAddress())
                    .zipCode(addressesResponse.getZipCode())
                    .phone(addressesResponse.getPhone())
                    .addressesType(addressesResponse.getAddressesType())
                    .build();
            return addresses;
        }

}
