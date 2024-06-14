package com.projectx.ecommerce.inventory.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchDto {
    private Long batchId;
    private String batchName;
    private Integer batchQty;
    private Long categoryId;
    private Long productId;
}
