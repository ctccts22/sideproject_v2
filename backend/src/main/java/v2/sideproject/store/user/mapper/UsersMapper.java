package v2.sideproject.store.user.mapper;

import v2.sideproject.store.user.dto.UsersDto;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.vo.request.UsersDetailsRequestVo;
import v2.sideproject.store.user.vo.response.UsersDetailsResponseVo;

public class UsersMapper {
    public static Users mapToUsers(UsersDto usersDto) {
        Roles roles = getRoles(usersDto);

        Users users = Users.builder()
                .email(usersDto.getEmail())
                .password(usersDto.getPassword())
                .name(usersDto.getName())
                .birth(usersDto.getBirth())
                .gender(usersDto.getGender())
                .status(usersDto.getStatus())
                .mobileCarrier(usersDto.getMobileCarrier())
                .phone(usersDto.getPhone())
                .roles(roles)
                .build();
                return users;
    }

    public static UsersDto mapToUsersDto(Users users) {
        String roleId = users.getRoles() != null ? users.getRoles().getRoleId() : null;

        UsersDto usersDto = UsersDto.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .roleId(roleId)
                .build();

        return usersDto;
    }

    public static Users mapToUsersDetailsRequestVo(UsersDetailsRequestVo usersDetailsRequestVo) {
        Users users = Users.builder()
                .email(usersDetailsRequestVo.getEmail())
                .password(usersDetailsRequestVo.getPassword())
                .name(usersDetailsRequestVo.getName())
                .birth(usersDetailsRequestVo.getBirth())
                .gender(usersDetailsRequestVo.getGender())
                .status(usersDetailsRequestVo.getStatus())
                .mobileCarrier(usersDetailsRequestVo.getMobileCarrier())
                .phone(usersDetailsRequestVo.getPhone())
                .build();
        return users;
    }

    public static UsersDetailsRequestVo mapFromUsersDetailsRequestVoToUsers(Users users) {
        UsersDetailsRequestVo usersDetailsRequestVo = UsersDetailsRequestVo.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .build();
        return usersDetailsRequestVo;
    }


    public static Users mapToUsersDetailsResponseVo(UsersDetailsResponseVo usersDetailsResponseVo, Roles roles) {
        Users users = Users.builder()
                .email(usersDetailsResponseVo.getEmail())
                .password(usersDetailsResponseVo.getPassword())
                .name(usersDetailsResponseVo.getName())
                .birth(usersDetailsResponseVo.getBirth())
                .gender(usersDetailsResponseVo.getGender())
                .status(usersDetailsResponseVo.getStatus())
                .mobileCarrier(usersDetailsResponseVo.getMobileCarrier())
                .phone(usersDetailsResponseVo.getPhone())
                .roles(roles)
                .build();
        return users;
    }

    public static UsersDetailsResponseVo mapFromUsersDetailsResponseVoToUsers(Users users) {
        UsersDetailsResponseVo usersDetailsResponseVo = UsersDetailsResponseVo.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birth(users.getBirth())
                .gender(users.getGender())
                .status(users.getStatus())
                .mobileCarrier(users.getMobileCarrier())
                .phone(users.getPhone())
                .build();
        return usersDetailsResponseVo;
    }

    private static Roles getRoles(UsersDto usersDto) {
        Roles roles = null;
        if (usersDto.getRoleId() != null) {
            roles = Roles.builder()
                    .roleId(usersDto.getRoleId())
                    .build();
        }
        return roles;
    }
}
