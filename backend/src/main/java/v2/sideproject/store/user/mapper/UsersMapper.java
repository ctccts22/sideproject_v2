package v2.sideproject.store.user.mapper;

import org.jooq.Record;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.models.dto.AddressesDto;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.request.UsersRegisterRequest;
import v2.sideproject.store.user.models.response.UsersDetailsResponse;
import v2.sideproject.store.user.models.response.UsersRegisterResponse;

import java.util.ArrayList;
import java.util.List;

import static v2.sideproject.store.tables.Roles.ROLES;
import static v2.sideproject.store.tables.Users.*;

public class UsersMapper {
    public static Users mapToUsersDetailsRequestDto(UsersRegisterRequest usersRegisterRequest) {
        Users users = Users.builder()
                .email(usersRegisterRequest.getEmail())
                .password(usersRegisterRequest.getPassword())
                .name(usersRegisterRequest.getName())
                .birth(usersRegisterRequest.getBirth())
                .gender(usersRegisterRequest.getGender())
                .status(usersRegisterRequest.getStatus())
                .mobileCarrier(usersRegisterRequest.getMobileCarrier())
                .phone(usersRegisterRequest.getPhone())
                .build();
        return users;
    }

    public static UsersRegisterRequest mapFromUsersDetailsRequestDtoToUsers(Users users) {
        UsersRegisterRequest usersRegisterRequest = UsersRegisterRequest.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .build();
        return usersRegisterRequest;
    }


    public static Users mapToUsersDetailsResponseDto(UsersRegisterResponse usersRegisterResponse, Roles roles) {
        Users users = Users.builder()
                .email(usersRegisterResponse.getEmail())
                .password(usersRegisterResponse.getPassword())
                .name(usersRegisterResponse.getName())
                .birth(usersRegisterResponse.getBirth())
                .gender(usersRegisterResponse.getGender())
                .status(usersRegisterResponse.getStatus())
                .mobileCarrier(usersRegisterResponse.getMobileCarrier())
                .phone(usersRegisterResponse.getPhone())
                .roles(roles)
                .build();
        return users;
    }

    public static UsersRegisterResponse mapFromUsersDetailsResponseDtoToUsers(Users users) {
        UsersRegisterResponse usersRegisterResponse = UsersRegisterResponse.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .build();
        return usersRegisterResponse;
    }

    public static UsersDetailsResponse mapFromUsersDtoToUsersDetailsResponse(UsersDto usersDto) {
        if (usersDto == null) {
            return null;
        }
        return UsersDetailsResponse.builder()
                .email(usersDto.getEmail())
                .name(usersDto.getName())
                .birth(usersDto.getBirth())
                .status(usersDto.getStatus())
                .gender(usersDto.getGender())
                .mobileCarrier(usersDto.getMobileCarrier())
                .phone(usersDto.getPhone())
                .roleName(usersDto.getRoles() != null ? usersDto.getRoles().getName() : null)
                .addressesList(usersDto.getAddressesList())
                .build();
    }

    public static UsersDto mapRecordToUser(Record record) {
//        List<AddressesDto> addresses = new ArrayList<>();
//
//        for (Record r : records) {
//            AddressesDto addressDto = AddressesDto.builder()
//                    .addressId(r.get(ADDRESSES.ADDRESS_ID))
//                    .street(r.get(ADDRESSES.STREET))
//                    .city(r.get(ADDRESSES.CITY))
//                    .postalCode(r.get(ADDRESSES.POSTAL_CODE))
//                    .build();
//            addresses.add(addressDto);
//        }
//
        return UsersDto.builder()
                .userId(record.get(USERS.USER_ID))
                .email(record.get(USERS.EMAIL))
                .password(record.get(USERS.PASSWORD))
                .name(record.get(USERS.NAME))
                .birth(record.get(USERS.BIRTH))
                .gender(Gender.valueOf(record.get(USERS.GENDER)))
                .status(UsersStatus.valueOf(record.get(USERS.STATUS)))
                .mobileCarrier(MobileCarrier.valueOf(record.get(USERS.MOBILE_CARRIER)))
                .phone(record.get(USERS.PHONE))
                .roles(RolesDto.builder()
                        .roleId(record.get(ROLES.ROLE_ID))
                        .name(RolesName.valueOf(record.get(ROLES.NAME)))
                        .build())
                .build();

    }
}
