package com.projectx.ecommerce.inventory.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewInventoryStockDto {
    private Integer srNo;
    private Long stockId;
    private String serialNumber;
    private String categoryName;
    private String productName;
    private String batchName;
    private String status;
}
