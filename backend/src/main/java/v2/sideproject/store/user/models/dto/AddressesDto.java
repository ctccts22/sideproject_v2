package v2.sideproject.store.user.models.dto;

import lombok.*;
import v2.sideproject.store.user.entity.Addresses;
import v2.sideproject.store.user.enums.AddressesType;

import java.io.Serializable;

/**
 * DTO for {@link Addresses}
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddressesDto implements Serializable {
    private Long addressesId;
    private String mainAddress;
    private String subAddress;
    private String zipCode;
    private String phone;
    private AddressesType addressesType;
}