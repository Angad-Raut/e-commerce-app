package com.projectx.ecommerce.product.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewCategoryDto {
    private Integer srNo;
    private Long categoryId;
    private String categoryName;
    private String categoryStatus;
}
