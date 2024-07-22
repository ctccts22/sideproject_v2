package v2.sideproject.store.user.mapper;

import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.models.request.UsersRegisterRequest;
import v2.sideproject.store.user.models.response.UsersRegisterResponse;

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
}
