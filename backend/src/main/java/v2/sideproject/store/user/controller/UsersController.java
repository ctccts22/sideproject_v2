package v2.sideproject.store.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import v2.sideproject.store.error.ErrorResponseDto;
import v2.sideproject.store.user.constants.UsersConstants;
import v2.sideproject.store.user.dto.request.AddressesRequestDto;
import v2.sideproject.store.user.dto.request.UsersDetailsRequestDto;
import v2.sideproject.store.user.dto.response.UsersDetailsResponseDto;
import v2.sideproject.store.user.dto.response.UsersStatusResponseDto;
import v2.sideproject.store.user.service.UsersService;

@Tag(
        name = "CRUD REST APIs for Users",
        description = "CRUD REST APIs in Users to CREATE, UPDATE, FETCH AND DELETE"
)
@RestController
@RequestMapping(path = "/api/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class UsersController {
    private final UsersService usersService;

    @Operation(
            summary = "Fetch Users Details RestAPI",
            description = "Rest API to fetch Users"
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
    @GetMapping(path = "/fetchAll")
    public ResponseEntity<Page<UsersDetailsResponseDto>> fetchAllUsersDetails() {
        Page<UsersDetailsResponseDto> usersDetailsResponseDto = usersService.fetchAllUsersDetails();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersDetailsResponseDto);
    }

    @Operation(
            summary = "Create User REST API",
            description = "REST API to create new User"
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
    public ResponseEntity<UsersStatusResponseDto> createUsers(@Valid
                                                                 @RequestBody UsersDetailsRequestDto usersDetailsRequestDto, AddressesRequestDto addressRequestDto) {
        usersService.createUsers(usersDetailsRequestDto, addressRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UsersStatusResponseDto(UsersConstants.STATUS_201, UsersConstants.MESSAGE_201));
    }
}
