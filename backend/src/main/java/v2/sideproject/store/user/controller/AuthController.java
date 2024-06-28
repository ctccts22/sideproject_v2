package v2.sideproject.store.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import v2.sideproject.store.user.constants.AuthConstants;
import v2.sideproject.store.user.vo.request.UsersLoginRequestVo;
import v2.sideproject.store.user.service.AuthService;
import v2.sideproject.store.user.vo.response.UsersInfoResponseVo;
import v2.sideproject.store.user.vo.response.UsersStatusResponseVo;


@RequestMapping(path = "/api/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

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
    public ResponseEntity<UsersStatusResponseVo> Login(@RequestBody UsersLoginRequestVo usersLoginRequestVo,
                                                       HttpServletResponse response) throws BadRequestException {
        authService.login(usersLoginRequestVo, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsersStatusResponseVo(AuthConstants.STATUS_200, AuthConstants.MESSAGE_Login_200));
    }

    @Operation(
            summary = "Logout Rest API",
            description = "Create Logout Request"
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

    }
    )
    @PostMapping(path ="/logout")
    public ResponseEntity<UsersStatusResponseVo> Logout(
            HttpServletResponse response,
            HttpServletRequest request) {
        authService.logout(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsersStatusResponseVo(AuthConstants.STATUS_200, AuthConstants.MESSAGE_Logout_200));
    }

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
    @GetMapping(path ="/accessToken")
    public ResponseEntity<UsersStatusResponseVo> getAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.getAccessToken(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsersStatusResponseVo(AuthConstants.STATUS_200, AuthConstants.MESSAGE_TOKEN_200));
    }

    @Operation(
            summary = "Response email and role to frontend Rest API",
            description = "This API can avoid to use local storage to save usersInfo. Any frontend lib&framework just utilize this API" +
                    "to use navigation guard(In order to protect get into the page against unknown users"
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
    @GetMapping(path ="/usersInfo")
    public ResponseEntity<UsersInfoResponseVo> getUserInfo() {
        UsersInfoResponseVo usersInfoResponseVo = authService.getUserInfo();
        return ResponseEntity.status(HttpStatus.OK)
                .body(usersInfoResponseVo);
    }

}
