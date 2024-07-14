package v2.sideproject.store.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import v2.sideproject.store.filter.JwtAuthenticationFilter;
import v2.sideproject.store.jwt.JwtTokenProvider;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.controller.UsersController;
import v2.sideproject.store.user.entity.Roles;
import v2.sideproject.store.user.entity.Users;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.RolesName;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.mapper.UsersMapper;
import v2.sideproject.store.user.service.impl.UsersServiceImpl;
import v2.sideproject.store.user.userDetails.CustomUserDetails;
import v2.sideproject.store.user.vo.request.UsersDetailsRequestVo;
import v2.sideproject.store.user.vo.response.UsersStatusResponseVo;
import v2.sideproject.store.users.security.WithMockCustomUser;


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
    private ObjectMapper objectMapper;

    @MockBean
    private UsersServiceImpl usersService;

    private Users users;
    private UsersDetailsRequestVo usersDetailsRequestVo;
    private UsersStatusResponseVo usersStatusResponseVo;
    private Roles roles;

    @BeforeEach
    void setup() {

        usersDetailsRequestVo = UsersDetailsRequestVo.builder()
                .email("testForJunit@test.com")
                .password("test")
                .name("홍길동")
                .birth("950315")
                .gender(Gender.MALE)
                .status(UsersStatus.APPROVED)
                .mobileCarrier(MobileCarrier.KT)
                .phone("000-0000-0000")
                .build();
}

    @DisplayName("user registration restAPI")
    @Test
    @WithMockCustomUser
    void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception {

        // given
        String requestBody = objectMapper.writeValueAsString(usersDetailsRequestVo);

        // void
        doNothing().when(usersService).createUsers(any(UsersDetailsRequestVo.class));

        // when
        ResultActions response = mockMvc.perform(post("/api/users/registration")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(UsersConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(UsersConstants.MESSAGE_201));

        verify(usersService, times(1)).createUsers(any(UsersDetailsRequestVo.class));
    }
}