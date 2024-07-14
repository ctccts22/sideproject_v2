package v2.sideproject.store.users.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.vo.request.UsersDetailsSearchConditionRequestVo;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    private Users users;

    private Roles roles;

    private UsersDetailsSearchConditionRequestVo usersDetailsSearchConditionRequestVo;

    @BeforeEach
    void setup() {
        roles = Roles.builder()
                .roleId(UUID.randomUUID().toString())
                .name(RolesName.CUSTOMER)
                .build();
        roles = rolesRepository.save(roles);
    }


    @DisplayName("JUnit test for registration users operation")
    @Test
    void givenUsersObject_whenSaveUsers_thenReturnSavedUsers() {
        var checkUser = Users.builder()
                .email("testForJunit@test.com")
                .password("test")
                .name("홍길동")
                .birth("950315")
                .gender(Gender.MALE)
                .status(UsersStatus.APPROVED)
                .mobileCarrier(MobileCarrier.KT)
                .phone("000-0000-0000")
                .roles(roles)
                .build();

        Users savedUsers = usersRepository.save(checkUser);

        System.out.println("checkUser " + checkUser);

        assertThat(savedUsers).isNotNull();
    }

    @DisplayName("Junit test for select users operation")
    @Test
    @Transactional
    void givenPageableAndSearchCondition_whenFindDetails_thenReturnQueryValues() {

        Pageable page = PageRequest.of(1, 10, Sort.by("a"));
    }
}