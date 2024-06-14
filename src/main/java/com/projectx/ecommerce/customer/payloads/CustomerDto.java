package com.projectx.ecommerce.customer.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private Long customerMobile;
    private String customerAddress;
    private String customerPassword;
}
