package com.projectx.ecommerce.inventory.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewBatchDto {
    private Integer srNo;
    private Long batchId;
    private String batchName;
    private String categoryName;
    private String productName;
    private String batchStatus;
    private Integer batchQty;
    private Integer remainingQty;
}
