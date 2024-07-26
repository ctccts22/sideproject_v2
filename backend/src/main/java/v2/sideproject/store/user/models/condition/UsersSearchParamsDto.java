package v2.sideproject.store.user.models.condition;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsersSearchParamsDto {
    private String name;
    private String birth;
}
