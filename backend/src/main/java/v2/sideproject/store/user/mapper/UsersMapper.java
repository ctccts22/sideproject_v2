package v2.sideproject.store.user.mapper;

import org.jooq.Record;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.*;
import v2.sideproject.store.user.models.dto.AddressesDto;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.request.UsersRegisterRequest;
import v2.sideproject.store.user.models.response.UsersDetailsResponse;
import v2.sideproject.store.user.models.response.UsersRegisterResponse;

import java.util.ArrayList;
import java.util.List;

import static v2.sideproject.store.tables.Addresses.ADDRESSES;
import static v2.sideproject.store.tables.Roles.ROLES;
import static v2.sideproject.store.tables.Users.*;

public class UsersMapper {

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
}
