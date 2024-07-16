package v2.sideproject.store.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressesId", nullable = false)
    private Long addressesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @Column(name = "mainAddress", nullable = false)
    private String mainAddress;

    @Column(name = "subAddress", nullable = false)
    private String subAddress;

    @Column(name = "zipCode", nullable = true)
    private String zipCode;

    @Column(name = "phone", nullable = false)
    private String phone;

}
