package v2.sideproject.store.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import v2.sideproject.store.security.WithMockCustomUser;
import v2.sideproject.store.user.controller.AuthController;
import v2.sideproject.store.user.models.enums.RolesName;
import v2.sideproject.store.user.models.vo.request.UsersLoginRequest;
import v2.sideproject.store.user.models.vo.response.UsersInfoResponse;
import v2.sideproject.store.user.service.impl.AuthServiceImpl;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthServiceImpl authService;

    @Test
    @WithMockCustomUser
    void login_shouldReturnStatusOk() throws Exception {
        UsersLoginRequest request = UsersLoginRequest.builder()
                .email("test@test.com")
                .password("test")
                .build();

        doNothing().when(authService).login(any(UsersLoginRequest.class), any(MockHttpServletResponse.class));

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser
    void logout_shouldReturnStatusOk() throws Exception {
        doNothing().when(authService).logout(any(), any());

        mockMvc.perform(post("/api/auth/logout")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser
    void getAccessToken_shouldReturnStatusOk() throws Exception {
        doNothing().when(authService).getAccessToken(any(), any());

        mockMvc.perform(get("/api/auth/accessToken")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("refreshToken", "testRefreshToken")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser
    void getUserInfo_shouldReturnStatusOk() throws Exception {
        UsersInfoResponse response = UsersInfoResponse.builder()
                .email("test@test.com")
                .roleName(RolesName.ADMIN)
                .build();

        when(authService.getUserInfo()).thenReturn(response);

        mockMvc.perform(get("/api/auth/usersInfo")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
