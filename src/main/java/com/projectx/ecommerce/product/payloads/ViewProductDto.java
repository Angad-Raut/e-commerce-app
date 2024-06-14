package com.projectx.ecommerce.product.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewProductDto {
    private Integer srNo;
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productCategory;
    private String productStatus;
}
