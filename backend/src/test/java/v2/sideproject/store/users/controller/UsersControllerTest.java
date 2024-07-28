package v2.sideproject.store.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import v2.sideproject.store.redis.utils.RestPage;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.controller.UsersController;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.models.enums.*;
import v2.sideproject.store.user.models.vo.request.AddressesRequest;
import v2.sideproject.store.user.models.vo.response.UsersDetailsResponse;
import v2.sideproject.store.user.service.impl.UsersServiceImpl;
import v2.sideproject.store.user.models.vo.request.UsersRegisterRequest;
import v2.sideproject.store.user.models.vo.response.UsersStatusResponse;
import v2.sideproject.store.security.WithMockCustomUser;


import java.util.Arrays;
import java.util.List;

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
    private UsersSearchParamsDto usersSearchParamsDto;
    private RestPage<UsersDetailsResponse> restPage;

    @BeforeEach
    void setup() {
        addressesRequest = AddressesRequest.builder()
                .mainAddress("관악구 봉천동")
                .subAddress("303호")
                .zipCode("90045")
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


        // fetchAll
        usersSearchParamsDto = UsersSearchParamsDto.builder()
                .email(null)
                .name(null)
                .birth(null)
                .gender(null)
                .status(null)
                .rolesName(null)
                .build();

        UsersDetailsResponse user1 = UsersDetailsResponse.builder()
                .email("user1@example.com")
                .name("User One")
                .birth("1990-01-01")
                .status(UsersStatus.APPROVED)
                .gender(Gender.MALE)
                .mobileCarrier(MobileCarrier.KT)
                .phone("010-1111-1111")
                .roleName(RolesName.ADMIN)
                .build();

        UsersDetailsResponse user2 = UsersDetailsResponse.builder()
                .email("user2@example.com")
                .name("User Two")
                .birth("1992-02-02")
                .status(UsersStatus.PENDING)
                .gender(Gender.FEMALE)
                .mobileCarrier(MobileCarrier.SK)
                .phone("010-2222-2222")
                .roleName(RolesName.CUSTOMER)
                .build();

        List<UsersDetailsResponse> userList = Arrays.asList(user1, user2);

        restPage = new RestPage<>(new PageImpl<>(userList, PageRequest.of(0, 10), userList.size()));
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

    @DisplayName("user fetchAll restAPI")
    @Test
    @WithMockCustomUser
    void givenParams_whenFetchAllUsers_thenReturnUserList() throws Exception {
        // Given
        given(usersService.fetchAllUsersDetails(any(UsersSearchParamsDto.class), any(Pageable.class)))
                .willReturn(restPage);

        // When
        ResultActions response = mockMvc.perform(get("/api/users/all")
                .param("email", "")
                .param("name", "")
                .param("birth", "")
                .param("gender", "")
                .param("status", "")
                .param("rolesName", "")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        );

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$.content[0].name").value("User One"))
                .andExpect(jsonPath("$.content[0].birth").value("1990-01-01"))
                .andExpect(jsonPath("$.content[1].email").value("user2@example.com"))
                .andExpect(jsonPath("$.content[1].name").value("User Two"))
                .andExpect(jsonPath("$.content[1].birth").value("1992-02-02"));
    }
}