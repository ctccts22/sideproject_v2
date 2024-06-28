package v2.sideproject.store.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UsersInfoResponseVo {

    private String email;
    private String roleName;
}
