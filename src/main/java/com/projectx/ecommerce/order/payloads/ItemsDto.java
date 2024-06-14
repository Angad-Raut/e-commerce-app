package com.projectx.ecommerce.order.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemsDto {
    private Long itemId;
    private Double price;
    private Integer quantity;
}
