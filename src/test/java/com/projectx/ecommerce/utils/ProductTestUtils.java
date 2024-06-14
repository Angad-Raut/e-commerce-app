package com.projectx.ecommerce.utils;

import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.product.entity.ProductDetails;
import com.projectx.ecommerce.product.payloads.EditProductDto;
import com.projectx.ecommerce.product.payloads.ProductDto;
import com.projectx.ecommerce.product.payloads.ViewProductDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductTestUtils {

    public static ProductDto toProductDto() {
        return ProductDto.builder()
                .productCategoryId(1L)
                .productPrice(1500.0)
                .productName("TubeLight")
                .productDescription("This is electronic product")
                .build();
    }
    public static ProductDetails toProductEntity() {
        return ProductDetails.builder()
                .productId(1L)
                .productCategory(CategoryTestUtils.toCategoryEntity())
                .productPrice(1500.0)
                .productName("TubeLight")
                .productDescription("This is electronic product")
                .productStatus(MessageUtils.ADDED_STATUS)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .build();
    }
    public static ProductDetails toProductEntityTwo() {
        return ProductDetails.builder()
                .productId(2L)
                .productCategory(CategoryTestUtils.toCategoryEntity())
                .productPrice(800.0)
                .productName("Sprace")
                .productDescription("This is electronic product")
                .productStatus(MessageUtils.ADDED_STATUS)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .build();
    }
    public static EditProductDto toProductEdit() {
        return EditProductDto.builder()
                .productId(1L)
                .productCategoryId(1L)
                .productPrice(1500.0)
                .productName("TubeLight")
                .productDescription("This is electronic product")
                .build();
    }
    public static List<ViewProductDto> toViewProductList(Boolean isInsert) {
        List<ViewProductDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewProductDto(1,1L,"TubeLight",1500.0,"Electronic",isInsert?MessageUtils.STATUS_INSERT:MessageUtils.STATUS_VERIFY));
        fetchList.add(new ViewProductDto(2,2L,"Sprace",800.0,"Electronic",isInsert?MessageUtils.STATUS_INSERT:MessageUtils.STATUS_VERIFY));
        return fetchList;
    }
    public static List<ProductDetails> toFetchList(Boolean isInsert) {
        List<ProductDetails> fetchList = new ArrayList<>();
        ProductDetails productDetails = toProductEntity();
        ProductDetails productDetails1 = toProductEntityTwo();
        if (isInsert) {
            productDetails.setProductStatus(MessageUtils.VERIFY_STATUS);
            productDetails1.setProductStatus(MessageUtils.VERIFY_STATUS);
        }
        fetchList.add(productDetails);
        fetchList.add(productDetails1);
        return fetchList;
    }
}
