package v2.sideproject.store.users.repository;

import com.querydsl.core.types.Order;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import v2.sideproject.store.user.entity.Addresses;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.*;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.repository.jpa.AddressesRepository;
import v2.sideproject.store.user.repository.jpa.RolesRepository;
import v2.sideproject.store.user.repository.jpa.UsersRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional // test rollback
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AddressesRepository addressesRepository;

    private Users users;

    private Roles roles;

    private Addresses addresses;


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

        var addresses1 = Addresses.builder()
                .mainAddress("관악구 봉천동")
                .subAddress("303호")
                .zipCode("90045")
                .phone("000-000-0000")
                .addressesType(AddressesType.HOME)
                .users(savedUsers)
                .build();
        var addresses2 = Addresses.builder()
                .mainAddress("관악구 봉천동")
                .subAddress("303호")
                .zipCode("90045")
                .phone("000-000-0000")
                .addressesType(AddressesType.HOME)
                .users(savedUsers)
                .build();
        addressesRepository.save(addresses1);
        addressesRepository.save(addresses2);
        System.out.println("checkUser " + checkUser);

        assertThat(savedUsers).isNotNull();
    }

    @DisplayName("Junit test for select users operation")
    @Test
    void givenPageableAndSearchCondition_whenFindDetails_thenReturnQueryValues() {

        Pageable page = PageRequest.of(1, 10);
        UsersSearchParamsDto searchParamsDto = new UsersSearchParamsDto();
    }
}