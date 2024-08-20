package v2.sideproject.store.user.models.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersStatusResponse {

    @Schema(
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;

}