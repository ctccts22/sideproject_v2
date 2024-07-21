package v2.sideproject.store.user.mapper;

import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.dto.request.UsersRegisterRequestDto;
import v2.sideproject.store.user.dto.response.UsersRegisterResponseDto;

public class UsersMapper {
    public static Users mapToUsersDetailsRequestDto(UsersRegisterRequestDto usersRegisterRequestDto) {
        Users users = Users.builder()
                .email(usersRegisterRequestDto.getEmail())
                .password(usersRegisterRequestDto.getPassword())
                .name(usersRegisterRequestDto.getName())
                .birth(usersRegisterRequestDto.getBirth())
                .gender(usersRegisterRequestDto.getGender())
                .status(usersRegisterRequestDto.getStatus())
                .mobileCarrier(usersRegisterRequestDto.getMobileCarrier())
                .phone(usersRegisterRequestDto.getPhone())
                .build();
        return users;
    }

    public static UsersRegisterRequestDto mapFromUsersDetailsRequestDtoToUsers(Users users) {
        UsersRegisterRequestDto usersRegisterRequestDto = UsersRegisterRequestDto.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .build();
        return usersRegisterRequestDto;
    }


    public static Users mapToUsersDetailsResponseDto(UsersRegisterResponseDto usersRegisterResponseDto, Roles roles) {
        Users users = Users.builder()
                .email(usersRegisterResponseDto.getEmail())
                .password(usersRegisterResponseDto.getPassword())
                .name(usersRegisterResponseDto.getName())
                .birth(usersRegisterResponseDto.getBirth())
                .gender(usersRegisterResponseDto.getGender())
                .status(usersRegisterResponseDto.getStatus())
                .mobileCarrier(usersRegisterResponseDto.getMobileCarrier())
                .phone(usersRegisterResponseDto.getPhone())
                .roles(roles)
                .build();
        return users;
    }

    public static UsersRegisterResponseDto mapFromUsersDetailsResponseDtoToUsers(Users users) {
        UsersRegisterResponseDto usersRegisterResponseDto = UsersRegisterResponseDto.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .build();
        return usersRegisterResponseDto;
    }
}
