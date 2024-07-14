package v2.sideproject.store.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import v2.sideproject.store.user.enums.RolesName;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Roles {

    @Id
    @Column(name = "roleId", nullable = false)
    private String roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private RolesName name;

//    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Users> usersSet = new HashSet<>();
}
