package com.projectx.ecommerce.product.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productDescription;
    private Long productCategoryId;
    private MultipartFile productImage;
}
