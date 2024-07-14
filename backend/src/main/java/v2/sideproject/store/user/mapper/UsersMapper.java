package v2.sideproject.store.user.mapper;

import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.dto.request.UsersDetailsRequestDto;
import v2.sideproject.store.user.dto.response.UsersDetailsResponseDto;

public class UsersMapper {
    public static Users mapToUsersDetailsRequestDto(UsersDetailsRequestDto usersDetailsRequestDto) {
        Users users = Users.builder()
                .email(usersDetailsRequestDto.getEmail())
                .password(usersDetailsRequestDto.getPassword())
                .name(usersDetailsRequestDto.getName())
                .birth(usersDetailsRequestDto.getBirth())
                .gender(usersDetailsRequestDto.getGender())
                .status(usersDetailsRequestDto.getStatus())
                .mobileCarrier(usersDetailsRequestDto.getMobileCarrier())
                .phone(usersDetailsRequestDto.getPhone())
                .build();
        return users;
    }

    public static UsersDetailsRequestDto mapFromUsersDetailsRequestDtoToUsers(Users users) {
        UsersDetailsRequestDto usersDetailsRequestDto = UsersDetailsRequestDto.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .build();
        return usersDetailsRequestDto;
    }


    public static Users mapToUsersDetailsResponseDto(UsersDetailsResponseDto usersDetailsResponseDto, Roles roles) {
        Users users = Users.builder()
                .email(usersDetailsResponseDto.getEmail())
                .password(usersDetailsResponseDto.getPassword())
                .name(usersDetailsResponseDto.getName())
                .birth(usersDetailsResponseDto.getBirth())
                .gender(usersDetailsResponseDto.getGender())
                .status(usersDetailsResponseDto.getStatus())
                .mobileCarrier(usersDetailsResponseDto.getMobileCarrier())
                .phone(usersDetailsResponseDto.getPhone())
                .roles(roles)
                .build();
        return users;
    }

    public static UsersDetailsResponseDto mapFromUsersDetailsResponseDtoToUsers(Users users) {
        UsersDetailsResponseDto usersDetailsResponseDto = UsersDetailsResponseDto.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .build();
        return usersDetailsResponseDto;
    }
}
