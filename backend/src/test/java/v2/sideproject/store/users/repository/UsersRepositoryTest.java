package v2.sideproject.store.users.repository;

import org.jooq.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.enums.*;
import v2.sideproject.store.user.models.vo.request.AddressesRequest;
import v2.sideproject.store.user.models.vo.request.UsersRegisterRequest;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UsersRepositoryTest {

    @Autowired
    DSLContext dsl;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    Long userId;

    @BeforeEach
    @DisplayName("setup saveUsers Test")
    void setup_givenUsersRegisterRequestAndRolesDto_whenSavesUsers_thenReturnUsersId() {
        // given
        UsersRegisterRequest usersRegisterRequest = UsersRegisterRequest.builder()
                .email("junitTest@test.com")
                .password("jUnitTest")
                .name("jUnitTest")
                .birth("950315")
                .gender(Gender.MALE)
                .status(UsersStatus.APPROVED)
                .mobileCarrier(MobileCarrier.KT)
                .phone("010-1111-1111")
                .build();
        RolesDto roles = RolesDto.builder()
                .roleId("3")
                .name(RolesName.CUSTOMER)
                .build();
        String encodedPassword = passwordEncoder.encode(usersRegisterRequest.getPassword());

        // when
        Long saveUsers = usersRepository.saveUsers(usersRegisterRequest, encodedPassword, roles);

        // then
        assertThat(saveUsers).isNotNull();
        assertThat(saveUsers).isGreaterThan(0);
    }

    @Test
    @DisplayName("findByEmail Test")
    void givenEmail_whenFindByEmail_thenReturnValue() {
        // give
        String email = "junitTest@test.com";

        // when
        Optional<UsersDto> usersDto = usersRepository.findByEmail(email);

        // then
        assertThat(usersDto).isPresent();
    }

    @Test
    @DisplayName("findByEmailWithRole Test")
    void givenEmail_whenFindByEmailWithRoles_thenReturnValue() {
        // given
        String email = "test@test.com";

        // when
        Optional<UsersDto> usersDto = usersRepository.findByEmailWithRole(email);

        // then
        assertThat(usersDto).isPresent();
    }

    @Test
    @DisplayName("findAllUsersDetailsByParams Test")
    void givenParams_whenFindAllUsersDetailsByParams_thenReturnValue() {
        // given
        UsersSearchParamsDto usersSearchParamsDto = UsersSearchParamsDto.builder()
                .name(null)
                .birth(null)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<UsersDto> usersDto = usersRepository.findAllUsersDetailsByParams(usersSearchParamsDto, pageable);

        // then
        assertThat(usersDto).isNotNull();
        assertThat(usersDto.hasContent()).isTrue();
        assertThat(usersDto.getTotalElements()).isGreaterThan(0);
        assertThat(usersDto.getContent()).isNotEmpty();
    }
    @Test
    @DisplayName("saveUsers Test")
    void givenUsersRegisterRequestAndUsersId_whenSavesAddresses_thenExecute() {
        // given
        UsersRegisterRequest usersRegisterRequest = UsersRegisterRequest.builder()
                .address(AddressesRequest.builder()
                        .mainAddress("2134 culver city")
                        .subAddress("303 unit")
                        .zipCode("90045")
                        .addressesType(AddressesType.HOME)
                        .build())
                .build();

        // when
        int saveUsers = usersRepository.saveAddresses(usersRegisterRequest, userId);

        // then
        assertThat(saveUsers).isGreaterThan(0);
    }

}
