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
import v2.sideproject.store.user.dto.request.AddressesRequestDto;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.mapper.UsersMapper;
import v2.sideproject.store.user.repository.RolesRepository;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.impl.UsersServiceImpl;
import v2.sideproject.store.user.dto.request.UsersDetailsRequestDto;
import v2.sideproject.store.user.dto.response.UsersDetailsResponseDto;

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

    private Users users;
    private UsersDetailsRequestDto usersDetailsRequestDto;
    private AddressesRequestDto addressesRequestDto;
    private Roles roles;

    @BeforeEach
    void setup() {
        usersDetailsRequestDto = UsersDetailsRequestDto.builder()
                .email("testForJunit@test.com")
                .password("test")
                .checkPassword("test")
                .name("홍길동")
                .birth("950315")
                .gender(Gender.MALE)
                .status(UsersStatus.APPROVED)
                .mobileCarrier(MobileCarrier.KT)
                .phone("000-0000-0000")
                .build();
        addressesRequestDto = AddressesRequestDto.builder()
                .mainAddress("관악구 봉천동")
                .subAddress("303호")
                .zipCode("90045")
                .phone("000-000-0000")
                .build();
        roles = Roles.builder()
                .roleId("3")
                .name(RolesName.CUSTOMER)
                .build();
    }

    @DisplayName("JUnit test for saveUsers method")
    @Test
    void givenSavedUser_whenOccurCreateService_thenReturnValue() {
        var usersDetailsResponseDto = UsersDetailsResponseDto.builder()
                .email(usersDetailsRequestDto.getEmail())
                .password(usersDetailsRequestDto.getPassword())
                .name(usersDetailsRequestDto.getName())
                .birth(usersDetailsRequestDto.getBirth())
                .gender(usersDetailsRequestDto.getGender())
                .status(usersDetailsRequestDto.getStatus())
                .mobileCarrier(usersDetailsRequestDto.getMobileCarrier())
                .phone(usersDetailsRequestDto.getPhone())
                .build();

        users = UsersMapper.mapToUsersDetailsResponseDto(usersDetailsResponseDto, roles);

        // given
        given(rolesRepository.findByName(RolesName.CUSTOMER))
                .willReturn(Optional.of(roles));

        given(usersRepository.save(any(Users.class)))
                .willReturn(users);

        // when
        usersService.createUsers(usersDetailsRequestDto, addressesRequestDto);

        // then
        verify(usersRepository, times(1)).save(any(Users.class));
    }
}
