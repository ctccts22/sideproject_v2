package v2.sideproject.store.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.controller.UsersController;
import v2.sideproject.store.user.enums.AddressesType;
import v2.sideproject.store.user.models.request.AddressesRequest;
import v2.sideproject.store.user.enums.Gender;
import v2.sideproject.store.user.enums.MobileCarrier;
import v2.sideproject.store.user.enums.UsersStatus;
import v2.sideproject.store.user.service.impl.UsersServiceImpl;
import v2.sideproject.store.user.models.request.UsersRegisterRequest;
import v2.sideproject.store.user.models.response.UsersStatusResponse;
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

    private UsersRegisterRequest usersRegisterRequest;
    private UsersStatusResponse usersStatusResponse;
    private AddressesRequest addressesRequest;

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
}

    @DisplayName("user registration restAPI")
    @Test
    @WithMockCustomUser
    void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception {

        // given
        String userBody = objectMapper.writeValueAsString(usersRegisterRequest);

        // void
        doNothing().when(usersService).createUsers(any(UsersRegisterRequest.class));

        // when
        ResultActions response = mockMvc.perform(post("/api/users/registration")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userBody)
        );

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(UsersConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(UsersConstants.MESSAGE_201));
    }
}