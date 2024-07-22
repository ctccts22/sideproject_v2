package v2.sideproject.store.user.models.condition;

import com.querydsl.core.types.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersOrderCondition {
    private String name;
    private String birth;
    private Order orderDirection;
}
