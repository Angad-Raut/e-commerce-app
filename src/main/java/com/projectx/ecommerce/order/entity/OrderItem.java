package com.projectx.ecommerce.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class OrderItem {
    private Long itemId;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
}
