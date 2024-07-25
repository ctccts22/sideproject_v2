package v2.sideproject.store.users.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import v2.sideproject.store.user.enums.*;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.request.AddressesRequest;
import v2.sideproject.store.user.repository.jooq.RolesRepository;
import v2.sideproject.store.user.repository.jooq.UsersRepository;
import v2.sideproject.store.user.service.impl.UsersServiceImpl;
import v2.sideproject.store.user.models.request.UsersRegisterRequest;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersServiceImpl usersService;

    private UsersRegisterRequest usersRegisterRequest;
    private AddressesRequest addressesRequest;
    private RolesDto roles;

    @BeforeEach
    void setup() {
        addressesRequest = AddressesRequest.builder()
                .mainAddress("관악구 봉천동")
                .subAddress("303호")
                .zipCode("90045")
                .phone("000-000-0000")
                .addressesType(AddressesType.HOME)
                .build();
        usersRegisterRequest = UsersRegisterRequest.builder()
                .email("testForJunit@test.com")
                .password("test")
                .checkPassword("test")
                .name("홍길동")
                .birth("950315")
                .gender(Gender.MALE)
                .status(UsersStatus.APPROVED)
                .mobileCarrier(MobileCarrier.KT)
                .phone("000-0000-0000")
                .address(addressesRequest)
                .build();
        roles = RolesDto.builder()
                .roleId("3")
                .name(RolesName.CUSTOMER)
                .build();
    }

    @DisplayName("JUnit test for saveUsers method")
    @Test
    void givenSavedUser_whenOccurCreateService_thenReturnValue() {

        // given
        given(rolesRepository.findByName(RolesName.CUSTOMER))
                .willReturn(Optional.of(roles));

        given(usersRepository.saveUsers(any(UsersRegisterRequest.class), any(RolesDto.class)))
                .willReturn(1L);

        // when
        usersService.createUsers(usersRegisterRequest);

        // then
        verify(usersRepository, times(1)).saveUsers(any(UsersRegisterRequest.class), any(RolesDto.class));
    }
}
