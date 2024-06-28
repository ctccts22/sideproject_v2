package v2.sideproject.store.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import v2.sideproject.store.company.entity.Companies;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.controller.UsersController;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.service.impl.UsersServiceImpl;
import v2.sideproject.store.user.vo.UsersDetailsRequestVo;
import v2.sideproject.store.user.vo.UsersDetailsResponseVo;

import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersServiceImpl usersService;


    private Users users;
    private UsersDetailsResponseVo usersDetailsResponseVo;
    private Roles roles;
    private Companies companies;

    @BeforeEach
    void setup() {
        roles = Roles.builder()
                .roleId(UUID.randomUUID().toString())
                .name(RolesName.PENDING)
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
                .status(UsersStatus.DELETED)
                .department("전략기획실")
                .position("대리")
                .cellphone("010-1111-1111")
                .telephone("02-2222-2222")
                .roles(roles)
                .companies(companies)
                .build();
    }

    @DisplayName("user registration restAPI")
    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception {

        // given
        String requestBody = objectMapper.writeValueAsString(users);

        // Mock occurrence of service method
        doNothing().when(usersService).createUsers(any(UsersDetailsRequestVo.class));

        ResultActions response = mockMvc.perform(post("/api/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(csrf())
        );

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(UsersConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(UsersConstants.MESSAGE_201));

        verify(usersService, times(1)).createUsers(any(UsersDetailsRequestVo.class));

    }
}
