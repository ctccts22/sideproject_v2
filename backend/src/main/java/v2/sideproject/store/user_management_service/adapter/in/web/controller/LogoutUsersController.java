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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import v2.sideproject.store.common.annotation.WebAdapter;
import v2.sideproject.store.user.constants.AuthConstants;
import v2.sideproject.store.user.models.vo.response.UsersStatusResponse;
import v2.sideproject.store.user_management_service.application.port.in.LogoutUsersUseCase;

@WebAdapter
@RestController
@RequestMapping("/api/users-service")
@RequiredArgsConstructor
public class LogoutUsersController {
    private final LogoutUsersUseCase logoutUsersUseCase;

    @Operation(
            summary = "Logout Rest API",
            description = "Logout Request"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP NO_CONTENT"
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
    @PostMapping(path = "/logout")
    public ResponseEntity<UsersStatusResponse> Logout(
            HttpServletResponse response,
            HttpServletRequest request) {
        logoutUsersUseCase.logoutUsers(request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new UsersStatusResponse(AuthConstants.STATUS_204, AuthConstants.MESSAGE_Logout_204));
    }
}
