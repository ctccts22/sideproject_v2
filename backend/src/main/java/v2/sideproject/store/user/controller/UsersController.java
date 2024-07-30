package v2.sideproject.store.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import v2.sideproject.store.error.ErrorResponseDto;
import v2.sideproject.store.redis.utils.RestPage;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.models.vo.request.ConfirmChangesRequest;
import v2.sideproject.store.user.models.vo.request.EmailVerificationRequest;
import v2.sideproject.store.user.models.vo.request.UpdateUserInfoRequest;
import v2.sideproject.store.user.models.vo.request.UsersRegisterRequest;
import v2.sideproject.store.user.models.vo.response.UsersDetailsResponse;
import v2.sideproject.store.user.models.vo.response.UsersOneDetailResponse;
import v2.sideproject.store.user.models.vo.response.UsersStatusResponse;
import v2.sideproject.store.user.models.condition.UsersSearchParamsDto;
import v2.sideproject.store.user.service.UsersService;

@Tag(
        name = "CRUD REST APIs for Users",
        description = "CRUD REST APIs in Users to CREATE, UPDATE, FETCH AND DELETE"
)
@RestController
@RequestMapping(path = "/api/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@Slf4j
public class UsersController {
    private final UsersService usersService;

    @Operation(
            summary = "Fetch Users Details RestAPI",
            description = "Rest API to fetch Users",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping(path = "/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestPage<UsersDetailsResponse>> fetchAllUsersDetails(
            @ModelAttribute UsersSearchParamsDto usersSearchParamsDto,
            Pageable pageable
    ) {
        RestPage<UsersDetailsResponse> usersDetailsResponseDto = usersService.fetchAllUsersDetails(usersSearchParamsDto, pageable);
        log.info("usersDetailsResponseDto : {}", usersDetailsResponseDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersDetailsResponseDto);
    }
    @Operation(
            summary = "Fetch One User RestAPI",
            description = "Rest API to get one User's info",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping(path = "/me")
    public ResponseEntity<UsersOneDetailResponse> getUserInfo() {
        UsersOneDetailResponse usersOneDetailResponse = usersService.getOneUserInfo();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersOneDetailResponse);
    }

    @Operation(
            summary = "Fetch Modify User Email RestAPI",
            description = "Rest API to Modify User's Email",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    /**
     * complex update logic generally post mapping would be better than put mapping
     */
    @PostMapping(path = "/users/update-email")
    public ResponseEntity<UsersStatusResponse> updateUserEmail(
            @RequestBody @Valid EmailVerificationRequest request) {
        usersService.modifyUsersEmail(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsersStatusResponse(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Fetch Modify User Info RestAPI",
            description = "Rest API to Modify User's Info",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/users/update-info")
    public ResponseEntity<UsersStatusResponse> updateUserInfo(@RequestBody @Valid UpdateUserInfoRequest request) {
        usersService.modifyUsersInfo(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsersStatusResponse(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200));
    }
    @PostMapping("/confirm-changes")
    public ResponseEntity<UsersStatusResponse> confirmChanges(@RequestBody @Valid ConfirmChangesRequest request) {
        usersService.confirmModifyUsersInfo(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsersStatusResponse(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200));
    }

    @PostMapping("/rollback-changes")
    public ResponseEntity<UsersStatusResponse> rollbackChanges() {
        usersService.rollbackChanges();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UsersStatusResponse(UsersConstants.STATUS_200, UsersConstants.MESSAGE_200));
    }


    @Operation(
            summary = "Create User REST API",
            description = "REST API to create new User",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping(path = "/registration")
    public ResponseEntity<UsersStatusResponse> createUsers(@Valid
                                                           @RequestBody UsersRegisterRequest usersRegisterRequest) {
        usersService.createUsers(usersRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UsersStatusResponse(UsersConstants.STATUS_201, UsersConstants.MESSAGE_201));
    }
}
