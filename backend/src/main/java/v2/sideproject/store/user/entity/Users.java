package v2.sideproject.store.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import v2.sideproject.store.company.entity.Companies;
import v2.sideproject.store.user.enums.UsersStatus;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Users extends UsersBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UsersStatus status;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "cellphone", nullable = false)
    private String cellphone;

    @Column(name = "telephone", nullable = true)
    private String telephone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Roles roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Companies companies;

}
