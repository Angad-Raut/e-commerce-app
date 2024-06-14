package com.projectx.ecommerce.utils;

import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.inventory.entity.BatchDetails;
import com.projectx.ecommerce.inventory.payloads.BatchDto;
import com.projectx.ecommerce.inventory.payloads.ViewBatchDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BatchTestUtils {

    public static BatchDto toBatchDto() {
        return BatchDto.builder()
                .batchName("BH1001")
                .batchQty(10)
                .categoryId(1L)
                .productId(1L)
                .build();
    }
    public static BatchDetails toBatchEntity() {
        return BatchDetails.builder()
                .batchId(1L)
                .batchStatus(true)
                .batchName("BH1001")
                .batchQty(10)
                .remainingQty(10)
                .updatedTime(new Date())
                .insertedTime(new Date())
                .productDetails(ProductTestUtils.toProductEntity())
                .productCategory(CategoryTestUtils.toCategoryEntity())
                .build();
    }
    public static BatchDetails toBatchEntityTwo() {
        return BatchDetails.builder()
                .batchId(2L)
                .batchStatus(true)
                .batchName("BH1002")
                .batchQty(15)
                .remainingQty(10)
                .updatedTime(new Date())
                .insertedTime(new Date())
                .productDetails(ProductTestUtils.toProductEntityTwo())
                .productCategory(CategoryTestUtils.toCategoryEntity())
                .build();
    }
    public static List<EntityIdAndValueDto> toDropDown() {
        List<EntityIdAndValueDto> fetchList = new ArrayList<>();
        fetchList.add(new EntityIdAndValueDto(1L,"BH1001"));
        fetchList.add(new EntityIdAndValueDto(2L,"BH1002"));
        return fetchList;
    }
    public static List<Object[]> toObjectDropDown() {
        List<Object[]> fetchList = new ArrayList<>();
        Object[] firstObj = new Object[]{1L,"BH1001"};
        Object[] secondObj = new Object[]{2L,"BH1002"};
        fetchList.add(firstObj);
        fetchList.add(secondObj);
        return fetchList;
    }
    public static List<ViewBatchDto> toViewBatchList() {
        List<ViewBatchDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewBatchDto(1,1L,"BH1001","Electronic","TubeLight","Active",10,10));
        fetchList.add(new ViewBatchDto(2,2L,"BH1002","Electronic","Sprace","Active",15,10));
        return fetchList;
    }
    public static List<BatchDetails> toFetchList() {
        List<BatchDetails> fetchList = new ArrayList<>();
        fetchList.add(toBatchEntity());
        fetchList.add(toBatchEntityTwo());
        return fetchList;
    }
}
