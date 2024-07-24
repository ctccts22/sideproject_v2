package v2.sideproject.store.user.mapper;

import org.jooq.Record;
import v2.sideproject.store.user.enums.AddressesType;
import v2.sideproject.store.user.models.dto.AddressesDto;
import v2.sideproject.store.user.models.response.AddressesResponse;
import v2.sideproject.store.user.entity.Addresses;
import v2.sideproject.store.user.entity.Users;

import static v2.sideproject.store.tables.Addresses.ADDRESSES;

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

    public static AddressesDto mapRecordToAddress(Record record) {
        return AddressesDto.builder()
                .addressesId(record.get(ADDRESSES.ADDRESSES_ID))
                .usersId(record.get(ADDRESSES.USER_ID))
                .mainAddress(record.get(ADDRESSES.MAIN_ADDRESS))
                .subAddress(record.get(ADDRESSES.SUB_ADDRESS))
                .zipCode(record.get(ADDRESSES.ZIP_CODE))
                .phone(record.get(ADDRESSES.PHONE))
                .addressesType(AddressesType.valueOf(record.get(ADDRESSES.PHONE)))
                .build();
    }

}
