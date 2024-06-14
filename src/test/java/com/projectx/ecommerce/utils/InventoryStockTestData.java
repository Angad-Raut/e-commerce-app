package com.projectx.ecommerce.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.inventory.payloads.BatchDto;
import com.projectx.ecommerce.inventory.payloads.InventoryStockDto;
import com.projectx.ecommerce.inventory.payloads.ViewBatchDto;
import com.projectx.ecommerce.inventory.payloads.ViewInventoryStockDto;

import java.util.ArrayList;
import java.util.List;

public class InventoryStockTestData {

    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static BatchDto createBatch() {
        return BatchDto.builder()
                .productId(1L)
                .categoryId(1L)
                .batchName("Batch101")
                .batchQty(10)
                .build();
    }
    public static BatchDto createBatchTwo() {
        return BatchDto.builder()
                .productId(1L)
                .categoryId(1L)
                .batchName("Batch102")
                .batchQty(15)
                .build();
    }
    public static List<EntityIdAndValueDto> batchDropDown() {
        List<EntityIdAndValueDto> fetchList = new ArrayList<>();
        fetchList.add(new EntityIdAndValueDto(1L,"Batch101"));
        fetchList.add(new EntityIdAndValueDto(2L,"Batch102"));
        return fetchList;
    }
    public static List<ViewBatchDto> getBatchList() {
        List<ViewBatchDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewBatchDto(1,1L,"Batch101","Electronic","Tubelight","Enabled",10,10));
        fetchList.add(new ViewBatchDto(2,2L,"Batch102","Electronic","Spress","Enabled",15,15));
        return fetchList;
    }
    public static InventoryStockDto createStock(Boolean isAdd) {
        return InventoryStockDto.builder()
                .stockId(isAdd?null:1L)
                .categoryId(1L)
                .productId(1L)
                .batchId(1L)
                .serialNumber("SR10001")
                .build();
    }
    public static InventoryStockDto createStockTwo(Boolean isAdd) {
        return InventoryStockDto.builder()
                .stockId(isAdd?null:2L)
                .categoryId(1L)
                .productId(1L)
                .batchId(2L)
                .serialNumber("SR20001")
                .build();
    }
    public static List<EntityIdAndValueDto> stockDropDown() {
        List<EntityIdAndValueDto> fetchList = new ArrayList<>();
        fetchList.add(new EntityIdAndValueDto(1L,"SR10001"));
        fetchList.add(new EntityIdAndValueDto(2L,"SR10002"));
        return fetchList;
    }
    public static List<ViewInventoryStockDto> getStockList() {
        List<ViewInventoryStockDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewInventoryStockDto(1,1L,"SR10001","Electronic","Tubelight","Batch101","InStock"));
        fetchList.add(new ViewInventoryStockDto(2,2L,"SR10002","Electronic","Sprace","Batch102","InStock"));
        return fetchList;
    }
}
