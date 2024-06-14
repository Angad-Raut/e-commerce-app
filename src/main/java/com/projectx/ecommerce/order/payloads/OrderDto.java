package com.projectx.ecommerce.order.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long customerId;
    private Integer discountType;
    private Double discountAmt;
    private Double orderAmount;
    private Double discountedAmt;
    private Set<ItemsDto> itemsDto =new HashSet<>();
}
