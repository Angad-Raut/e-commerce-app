package com.projectx.ecommerce.product.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditProductDto {
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productDescription;
    private Long productCategoryId;
    private byte[] productImage;
}
