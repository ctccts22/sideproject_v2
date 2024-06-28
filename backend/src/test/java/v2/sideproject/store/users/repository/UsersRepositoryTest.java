package v2.sideproject.store.users.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import v2.sideproject.store.company.entity.Companies;
import v2.sideproject.store.company.repository.CompaniesRepository;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.vo.request.UsersDetailsSearchConditionRequestVo;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private CompaniesRepository companiesRepository;

    private Users users;

    private Companies companies;

    private Roles roles;

    private UsersDetailsSearchConditionRequestVo usersDetailsSearchConditionRequestVo;

    @BeforeEach
    void setup() {
        roles = Roles.builder()
                .roleId(UUID.randomUUID().toString())
                .name(RolesName.CLIENT)
                .build();
        roles = rolesRepository.save(roles);

        companies = Companies.builder()
                .name("Neurolines")
                .parentCompany(null)
                .address("Seoul")
                .build();
        companies = companiesRepository.save(companies);
    }


    @DisplayName("JUnit test for registration users operation")
    @Test
    void givenUsersObject_whenSaveUsers_thenReturnSavedUsers() {
        var checkUser = Users.builder()
                .email("test@test.com")
                .password("test12345")
                .name("홍길동")
                .status(UsersStatus.APPROVED)
                .department("전략기획실")
                .position("대리")
                .cellphone("010-1111-1111")
                .telephone("02-2222-2222")
                .roles(roles)
                .companies(companies)
                .build();

        Users savedUsers = usersRepository.save(checkUser);

        System.out.println("checkUser " + checkUser);

        assertThat(savedUsers).isNotNull();
    }

    @DisplayName("Junit test for select users operation")
    @Test
    void givenPageableAndSearchCondition_whenFindDetails_thenReturnQueryValues() {

        Pageable page = PageRequest.of(1, 10, Sort.by("a"));
    }
}