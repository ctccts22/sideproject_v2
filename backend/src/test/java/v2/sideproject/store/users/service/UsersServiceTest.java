package v2.sideproject.store.users.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import v2.sideproject.store.redis.utils.RestPage;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.dto.RolesDto;
import v2.sideproject.store.user.models.dto.UsersDto;
import v2.sideproject.store.user.models.enums.*;
import v2.sideproject.store.user.models.vo.request.AddressesRequest;
import v2.sideproject.store.user.models.vo.response.UsersDetailsResponse;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.impl.UsersServiceImpl;
import v2.sideproject.store.user.models.vo.request.UsersRegisterRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

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

    private String encodedPassword;

    @BeforeEach
    void setup() {
        addressesRequest = AddressesRequest.builder()
                .mainAddress("관악구 봉천동")
                .subAddress("303호")
                .zipCode("90045")
                .addressesType(AddressesType.HOME)
                .build();
        usersRegisterRequest = UsersRegisterRequest.builder()
                .email("junitTest@test.com")
                .password("jUnitTest")
                .checkPassword("jUnitTest")
                .name("jUnitTest")
                .birth("950315")
                .gender(Gender.MALE)
                .status(UsersStatus.APPROVED)
                .mobileCarrier(MobileCarrier.KT)
                .phone("010-1111-1111")
                .address(addressesRequest)
                .build();
        roles = RolesDto.builder()
                .roleId("3")
                .name(RolesName.CUSTOMER)
                .build();

        encodedPassword = passwordEncoder.encode(usersRegisterRequest.getPassword());
    }

    @DisplayName("JUnit test for createUsers method")
    @Test
    void givenSavedUser_whenOccurCreateService_thenReturnValue() {
        Long expectedUserId = 1L;

        // given
        given(rolesRepository.findByName(RolesName.CUSTOMER))
                .willReturn(Optional.of(roles));
        given(usersRepository.saveUsers(usersRegisterRequest, encodedPassword, roles))
                .willReturn(expectedUserId);

        // when
        usersService.createUsers(usersRegisterRequest);

        // then
        verify(rolesRepository).findByName(RolesName.CUSTOMER);
        verify(usersRepository).saveUsers(usersRegisterRequest, encodedPassword, roles);
    }

    @DisplayName("Junit test for fetchAllUsersDetails method")
    @Test
    void givenParams_whenOccurMethod_thenReturnValue() {
        Pageable pageable = PageRequest.of(1, 10);
        UsersSearchParamsDto params = UsersSearchParamsDto.builder()
                .email(null)
                .name(null)
                .birth(null)
                .gender(null)
                .status(null)
                .rolesName(null)
                .build();

        List<UsersDto> usersDtoList = Arrays.asList(
                UsersDto.builder()
                        .email("test1@example.com")
                        .name("User1")
                        .birth("1990-01-01")
                        .gender(Gender.MALE)
                        .status(UsersStatus.APPROVED)
                        .mobileCarrier(MobileCarrier.KT)
                        .phone("010-1234-5678")
                        .roles(RolesDto.builder().name(RolesName.ADMIN).build())
                        .build(),
                UsersDto.builder()
                        .email("test2@example.com")
                        .name("User2")
                        .birth("1992-02-02")
                        .gender(Gender.FEMALE)
                        .status(UsersStatus.PENDING)
                        .mobileCarrier(MobileCarrier.SK)
                        .phone("010-8765-4321")
                        .roles(RolesDto.builder().name(RolesName.CUSTOMER).build())
                        .build()
        );
        Page<UsersDto> usersDtoPage = new PageImpl<>(usersDtoList, pageable, usersDtoList.size());
        // given
        given(usersRepository.findAllUsersDetailsByParams(params, pageable))
                .willReturn(usersDtoPage);

        // When
        RestPage<UsersDetailsResponse> result = usersService.fetchAllUsersDetails(params, pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(usersDtoList.size());

        verify(usersRepository).findAllUsersDetailsByParams(params, pageable);
    }

}
