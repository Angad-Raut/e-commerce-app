package com.projectx.ecommerce.setting.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountDto {
    private Long id;
    private Integer discountType;
    private Double discount;
}
