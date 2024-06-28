package v2.sideproject.store.user.mapper;

import v2.sideproject.store.company.entity.Companies;
import v2.sideproject.store.user.dto.UsersDto;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.vo.request.UsersDetailsRequestVo;
import v2.sideproject.store.user.vo.response.UsersDetailsResponseVo;

public class UsersMapper {
    public static Users mapToUsers(UsersDto usersDto) {
        Roles roles = getRoles(usersDto);
        Companies companies = getCompanies(usersDto);

        Users users = Users.builder()
                .email(usersDto.getEmail())
                .name(usersDto.getName())
                .status(usersDto.getStatus())
                .department(usersDto.getDepartment())
                .position(usersDto.getPosition())
                .cellphone(usersDto.getCellphone())
                .roles(roles)
                .companies(companies)
                .build();
                return users;
    }

    public static UsersDto mapToUsersDto(Users users) {
        String roleId = users.getRoles() != null ? users.getRoles().getRoleId() : null;
        Long companyId = users.getCompanies() != null ? users.getCompanies().getCompanyId() : null;

        UsersDto usersDto = UsersDto.builder()
                .userId(users.getUserId())
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .status(users.getStatus())
                .department(users.getDepartment())
                .position(users.getPosition())
                .cellphone(users.getCellphone())
                .telephone(users.getTelephone())
                .roleId(roleId)
                .companyId(companyId)
                .build();

        return usersDto;
    }

    public static Users mapToUsersDetailsRequestVo(UsersDetailsRequestVo usersDetailsRequestVo) {
        Users users = Users.builder()
                .email(usersDetailsRequestVo.getEmail())
                .password(usersDetailsRequestVo.getPassword())
                .name(usersDetailsRequestVo.getName())
                .department(usersDetailsRequestVo.getDepartment())
                .position(usersDetailsRequestVo.getPosition())
                .cellphone(usersDetailsRequestVo.getCellphone())
                .telephone(usersDetailsRequestVo.getTelephone())
                .build();
        return users;
    }

    public static UsersDetailsRequestVo mapFromUsersDetailsRequestVoToUsers(Users users) {
        UsersDetailsRequestVo usersDetailsRequestVo = UsersDetailsRequestVo.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .department(users.getDepartment())
                .position(users.getPosition())
                .cellphone(users.getCellphone())
                .telephone(users.getTelephone())
                .build();
        return usersDetailsRequestVo;
    }


    public static Users mapToUsersDetailsResponseVo(UsersDetailsResponseVo usersDetailsResponseVo, Roles roles, Companies companies) {
        Users users = Users.builder()
                .email(usersDetailsResponseVo.getEmail())
                .password(usersDetailsResponseVo.getPassword())
                .name(usersDetailsResponseVo.getName())
                .status(usersDetailsResponseVo.getStatus())
                .department(usersDetailsResponseVo.getDepartment())
                .position(usersDetailsResponseVo.getPosition())
                .cellphone(usersDetailsResponseVo.getCellphone())
                .telephone(usersDetailsResponseVo.getTelephone())
                .roles(roles)
                .companies(companies)
                .build();
        return users;
    }

    public static UsersDetailsResponseVo mapFromUsersDetailsResponseVoToUsers(Users users) {
        UsersDetailsResponseVo usersDetailsResponseVo = UsersDetailsResponseVo.builder()
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .status(users.getStatus())
                .department(users.getDepartment())
                .position(users.getPosition())
                .cellphone(users.getCellphone())
                .telephone(users.getTelephone())
                .companyName(users.getCompanies().getName())
                .address(users.getCompanies().getAddress())
                .build();
        return usersDetailsResponseVo;
    }

    private static Companies getCompanies(UsersDto usersDto) {
        Companies companies = null;
        if (usersDto.getCompanyId() != null) {
            companies = Companies.builder()
                    .companyId(usersDto.getCompanyId())
                    .build();
        }
        return companies;
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
