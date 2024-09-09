package v2.sideproject.store.user_management_service.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import v2.sideproject.store.common.annotation.WebAdapter;
import v2.sideproject.store.user_management_service.adapter.in.web.response.RefreshUsersResponse;
import v2.sideproject.store.user_management_service.application.port.in.RefreshUsersUseCase;


@WebAdapter
@RestController
@Slf4j
@RequestMapping("/api/users-service")
@RequiredArgsConstructor
public class RefreshUsersController {
    private final RefreshUsersUseCase refreshUsersUseCase;


    @Operation(
            summary = "Fetch accessToken on header Rest API",
            description = "call accessToken with refreshToken then fetch accessToken on header." +
                    "Axios.interceptor on any frontend logic just need to call this API then possible to put accessToken on header"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )

    })
    @GetMapping(path ="/refresh")
    public ResponseEntity<RefreshUsersResponse> getAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // return response value
        RefreshUsersResponse refreshUsersResponse = refreshUsersUseCase.getUserInfo(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(refreshUsersResponse);
    }
}
