package v2.sideproject.store.comm.order.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import v2.sideproject.store.comm.order.enums.OrderStatus;
import v2.sideproject.store.user.entity.Users;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Orders {

    @Id
    @Column(name = "orderId", nullable = false)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @Column(name = "orderDate", nullable = false)
    private String orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "orderStatus", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "totalPrice", nullable = false)
    private Integer totalPrice;
}
