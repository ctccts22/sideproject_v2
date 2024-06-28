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
import v2.sideproject.store.company.entity.Companies;
import v2.sideproject.store.company.repository.CompaniesRepository;
import v2.sideproject.store.exception.UsersAlreadyExistsException;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.mapper.UsersMapper;
import v2.sideproject.store.user.repository.UsersRepository;
import v2.sideproject.store.user.service.impl.UsersServiceImpl;
import v2.sideproject.store.user.vo.request.UsersDetailsRequestVo;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private CompaniesRepository companiesRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    private Users users;
    private UsersDetailsRequestVo usersDetailsRequestVo;
    private Roles roles;
    private Companies companies;

    @BeforeEach
    void setup() {
        roles = Roles.builder()
                .roleId(UUID.randomUUID().toString())
                .name(RolesName.ADMIN)
                .build();
        companies = Companies.builder()
                .companyId(1L)
                .name("Neurolines")
                .parentCompany(null)
                .address("Seoul")
                .build();
        users = Users.builder()
                .userId(1L)
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
        // UserDto for using service layer
        usersDetailsRequestVo = UsersMapper.mapFromUsersDetailsRequestVoToUsers(users);
    }

    @DisplayName("JUnit test for saveUsers method")
    @Test
    void givenSavedUser_whenOccurCreateService_thenReturnValue() {
        // given
        given(usersRepository.findByCellphone(usersDetailsRequestVo.getCellphone()))
                .willReturn(Optional.empty());
        given(companiesRepository.save(any(Companies.class)))
                .willReturn(companies);
        given(usersRepository.save(any(Users.class)))
                .willReturn(users);

        System.out.println(users.toString());

        // when
        usersService.createUsers(usersDetailsRequestVo);

        // then
        verify(usersRepository, times(1)).findByCellphone(usersDetailsRequestVo.getCellphone());
        verify(companiesRepository, times(1)).save(any(Companies.class));
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @DisplayName("JUnit test for createUsers method when user already exists")
    @Test
    void givenExistingUser_whenCreateUsers_thenThrowException() {
        // given
        given(usersRepository.findByCellphone(usersDetailsRequestVo.getCellphone()))
                .willReturn(Optional.of(users));

        // when & then
        assertThrows(UsersAlreadyExistsException.class, () -> usersService.createUsers(usersDetailsRequestVo));

        verify(usersRepository, times(1)).findByCellphone(usersDetailsRequestVo.getCellphone());
        verify(usersRepository, never()).save(any(Users.class));
    }
}
