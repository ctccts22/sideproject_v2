package v2.sideproject.store.user_management_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import v2.sideproject.store.user_management_service.domain.enums.AddressType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Addresses {
    private Long addressesId;
    private String mainAddress;
    private String subAddress;
    private String zipCode;
    private AddressType addressType;
    // @ManyToOne
    private Users userPK;
}
