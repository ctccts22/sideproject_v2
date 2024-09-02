package v2.sideproject.store.user_management_service.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import v2.sideproject.store.common.annotation.WebAdapter;
import v2.sideproject.store.user.constants.AuthConstants;
import v2.sideproject.store.user.models.vo.response.UsersStatusResponse;
import v2.sideproject.store.user_management_service.adapter.in.web.request.LoginUsersRequest;
import v2.sideproject.store.user_management_service.application.port.in.LoginUsersUseCase;

@WebAdapter
@RestController
@RequestMapping("/api/users-service")
@RequiredArgsConstructor
public class LoginUsersController {

    private final LoginUsersUseCase loginUsersUseCase;

    @Operation(
            summary = "Login Rest API",
            description = "Create Login Request"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS OK"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP STATUS UNAUTHORIZED"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP STATUS NOT FOUND"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )

    }
    )
    @PostMapping(path = "/login")
    public ResponseEntity<UsersStatusResponse> Login(@RequestBody LoginUsersRequest loginRequest,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {


        loginUsersUseCase.loginByRequestInfo(loginRequest, request, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsersStatusResponse(AuthConstants.STATUS_200, AuthConstants.MESSAGE_Login_200));
    }
}
