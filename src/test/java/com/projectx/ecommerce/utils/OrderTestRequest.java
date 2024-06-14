package com.projectx.ecommerce.utils;

import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderTestRequest {
    private CustomerDetails customerDetails;
    private PaymentDetails paymentDetails;
    private Long productId;
    private Integer quantity;
    private Double price;
}
