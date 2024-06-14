package com.projectx.ecommerce.order.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewOrderPaymentDto {
    private Integer srNo;
    private Long orderId;
    private Long paymentId;
    private Long customerId;
    private String orderNumber;
    private String orderDate;
    private String customerName;
    private Double orderAmount;
    private Double paymentAmount;
}
