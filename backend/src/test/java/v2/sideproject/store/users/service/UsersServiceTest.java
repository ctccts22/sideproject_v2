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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import v2.sideproject.store.exception.UsersAlreadyExistsException;
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
import v2.sideproject.store.user.vo.request.UsersDetailsRequestVo;

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
    private UsersDetailsRequestVo usersDetailsRequestVo;
    private Roles roles;

    @BeforeEach
    void setup() {
        roles = Roles.builder()
                .roleId("1")
                .name(RolesName.CUSTOMER)
                .build();
        users = Users.builder()
                .email("testForJunit@test.com")
                .password(passwordEncoder.encode("test"))
                .name("홍길동")
                .birth("950315")
                .gender(Gender.MALE)
                .status(UsersStatus.APPROVED)
                .mobileCarrier(MobileCarrier.KT)
                .phone("000-0000-0000")
                .roles(roles)
                .build();
        // UserDto for using service layer
        usersDetailsRequestVo = UsersMapper.mapFromUsersDetailsRequestVoToUsers(users);

    }

    @DisplayName("JUnit test for saveUsers method")
    @Test
    void givenSavedUser_whenOccurCreateService_thenReturnValue() {

        // given
        given(rolesRepository.findByName(RolesName.CUSTOMER))
                .willReturn(Optional.of(roles));

        given(usersRepository.save(any(Users.class)))
                .willReturn(users);

        // when
        usersService.createUsers(usersDetailsRequestVo);

        // then
        verify(usersRepository, times(1)).save(any(Users.class));
    }

//    @DisplayName("JUnit test for createUsers method when user already exists")
//    @Test
//    void givenExistingUser_whenCreateUsers_thenThrowException() {
//        // given
//        given(usersRepository.findByCellphone(usersDetailsRequestVo.getCellphone()))
//                .willReturn(Optional.of(users));
//
//        // when & then
//        assertThrows(UsersAlreadyExistsException.class, () -> usersService.createUsers(usersDetailsRequestVo));
//
//        verify(usersRepository, times(1)).findByCellphone(usersDetailsRequestVo.getCellphone());
//        verify(usersRepository, never()).save(any(Users.class));
//    }
}
