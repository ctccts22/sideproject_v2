package v2.sideproject.store.user_management_service.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class TokensDto {
    @Schema(description = "Access Token", example = "jwt token")
    private String accessToken;
    @Schema(description = "Token type", example = "Bearer")
    private String type = "Bearer";
    @Schema(description = "Refresh Token", example = "uuid")
    private String refreshToken;
}
