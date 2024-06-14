package com.projectx.ecommerce.order.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewOrdersDto {
    private Integer srNo;
    private Long orderId;
    private Long customerId;
    private String customerName;
    private String orderNumber;
    private String orderDate;
    private Double orderAmount;
    private String orderStatus;
}
