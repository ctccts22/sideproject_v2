package v2.sideproject.store.user.models.condition;

import lombok.*;

@Getter
@Setter // ReqParams need setter
public class UsersSearchParamsDto {
    private String name;
    private String birth;
}
