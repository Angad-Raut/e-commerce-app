package com.projectx.ecommerce.utils;

import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.inventory.entity.InventoryStockDetails;
import com.projectx.ecommerce.inventory.payloads.InventoryStockDto;
import com.projectx.ecommerce.inventory.payloads.ViewInventoryStockDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryStockTestUtils {

    public static InventoryStockDto toInventoryStockDto(Boolean isAdd) {
        return InventoryStockDto.builder()
                .stockId(isAdd?null:1L)
                .productId(1L)
                .batchId(1L)
                .categoryId(1L)
                .serialNumber("SR10001")
                .build();
    }

    public static InventoryStockDetails toInventoryStockEntity() {
        return InventoryStockDetails.builder()
                .stockId(1L)
                .productCategory(CategoryTestUtils.toCategoryEntity())
                .productDetails(ProductTestUtils.toProductEntity())
                .batchDetails(BatchTestUtils.toBatchEntity())
                .serialNumber("SR10001")
                .status(true)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .build();
    }
    public static InventoryStockDetails toInventoryStockEntityTwo() {
        return InventoryStockDetails.builder()
                .stockId(2L)
                .productCategory(CategoryTestUtils.toCategoryEntity())
                .productDetails(ProductTestUtils.toProductEntity())
                .batchDetails(BatchTestUtils.toBatchEntity())
                .serialNumber("SR10002")
                .status(true)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .build();
    }
    public static List<EntityIdAndValueDto> toDropDown() {
        List<EntityIdAndValueDto> fetchList = new ArrayList<>();
        fetchList.add(new EntityIdAndValueDto(1L,"SR10001"));
        fetchList.add(new EntityIdAndValueDto(2L,"SR10002"));
        return fetchList;
    }
    public static List<InventoryStockDetails> toFetchList() {
        List<InventoryStockDetails> fetchList = new ArrayList<>();
        fetchList.add(toInventoryStockEntity());
        fetchList.add(toInventoryStockEntityTwo());
        return fetchList;
    }
    public static List<ViewInventoryStockDto> toViewStockList() {
        List<ViewInventoryStockDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewInventoryStockDto(1,1L,"SR10001","Electronic","TubeLight","BH1001","Active"));
        fetchList.add(new ViewInventoryStockDto(2,2L,"SR10002","Electronic","TubeLight","BH1001","Active"));
        return fetchList;
    }
}
