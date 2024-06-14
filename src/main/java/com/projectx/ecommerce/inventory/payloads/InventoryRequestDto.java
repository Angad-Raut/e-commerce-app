package com.projectx.ecommerce.inventory.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequestDto {
    private Long categoryId;
    private Long productId;
    private Long batchId;
}
