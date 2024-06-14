package com.projectx.ecommerce.utils;

import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.payloads.CategoryDto;
import com.projectx.ecommerce.product.payloads.ViewCategoryDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryTestUtils {

    public static CategoryDto toCategoryDto() {
        return CategoryDto.builder()
                .categoryId(1L)
                .categoryName("Electronic")
                .build();
    }
    public static ProductCategory toCategoryEntity() {
        return ProductCategory.builder()
                .categoryId(1L)
                .categoryName("Electronic")
                .categoryStatus(true)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .build();
    }

    public static ProductCategory toCategoryEntityTwo() {
        return ProductCategory.builder()
                .categoryId(2L)
                .categoryName("Food")
                .categoryStatus(true)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .build();
    }
    public static List<ProductCategory> toProductCategoryList() {
        List<ProductCategory> fetchList = new ArrayList<>();
        fetchList.add(toCategoryEntity());
        fetchList.add(toCategoryEntityTwo());
        return fetchList;
    }
    public static List<EntityIdAndValueDto> toDropDown() {
        List<EntityIdAndValueDto> fetchList = new ArrayList<>();
        fetchList.add(new EntityIdAndValueDto(1L,"Electronic"));
        fetchList.add(new EntityIdAndValueDto(2L,"Food"));
        return fetchList;
    }
    public static List<Object[]> objectDropDown() {
        List<Object[]> fetchList = new ArrayList<>();
        Object[] firstObj = new Object[]{1L,"Electronic"};
        Object[] secondObj = new Object[]{2L,"Food"};
        fetchList.add(firstObj);
        fetchList.add(secondObj);
        return fetchList;
    }
    public static List<ViewCategoryDto> toCategoryList() {
        List<ViewCategoryDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewCategoryDto(1,1L,"Electronic","Enabled"));
        fetchList.add(new ViewCategoryDto(2,2L,"Food","Enabled"));
        return fetchList;
    }
}
