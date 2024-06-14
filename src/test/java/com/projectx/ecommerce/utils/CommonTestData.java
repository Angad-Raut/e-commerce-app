package com.projectx.ecommerce.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.customer.payloads.CustomerDto;
import com.projectx.ecommerce.customer.payloads.ViewCustomerDto;
import com.projectx.ecommerce.product.payloads.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommonTestData {

    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static CustomerDto createCustomerRequest() {
        return CustomerDto.builder()
                .customerId(1L)
                .customerName("Angad Raut")
                .customerEmail("angadraut75@gmail.com")
                .customerMobile(9766945760L)
                .customerAddress("Pune, Maharashtra")
                .build();
    }

    public static CustomerDto createCustomer() {
        return CustomerDto.builder()
                .customerId(2L)
                .customerName("Shital Raut")
                .customerEmail("shitalraut95@gmail.com")
                .customerMobile(9834177005L)
                .customerAddress("Pune, Maharashtra")
                .build();
    }
    public static List<EntityIdAndValueDto> customerDropDown() {
        List<EntityIdAndValueDto> list = new ArrayList<>();
        list.add(new EntityIdAndValueDto(1L,"Angad Raut"));
        list.add(new EntityIdAndValueDto(2L,"Shital Raut"));
        return list;
    }

    public static List<EntityIdAndValueDto> categoryDropDown() {
        List<EntityIdAndValueDto> list = new ArrayList<>();
        list.add(new EntityIdAndValueDto(1L,"Electronic"));
        list.add(new EntityIdAndValueDto(2L,"Food"));
        return list;
    }

    public static List<EntityIdAndValueDto> productDropDown() {
        List<EntityIdAndValueDto> list = new ArrayList<>();
        list.add(new EntityIdAndValueDto(1L,"TubeLight"));
        list.add(new EntityIdAndValueDto(2L,"Sprace"));
        return list;
    }
    public static List<ViewCategoryDto> getCategoryList(Boolean isEnable) {
        List<ViewCategoryDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewCategoryDto(1,1L,"Electronic","Enabled"));
        fetchList.add(new ViewCategoryDto(2,2L,"Food",isEnable?"Enabled":"Disabled"));
        return fetchList;
    }
    public static List<ViewCustomerDto> getCustomerList(Boolean isActive) {
        List<ViewCustomerDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewCustomerDto(1,1L,"Angad Raut",
                "angadraut75@gmail.com",9766945760L,"Enabled"));
        fetchList.add(new ViewCustomerDto(2,2L,"Shital Raut",
                "shitalraut95@gmail.com",9834177005L,isActive?"Enabled":"Disabled"));
        return fetchList;
    }
    public static CategoryDto createCategory() {
        return CategoryDto.builder()
                .categoryId(1L)
                .categoryName("Electronic")
                .build();
    }
    public static CategoryDto createCategoryTwo() {
        return CategoryDto.builder()
                .categoryId(2L)
                .categoryName("Food")
                .build();
    }
    public static ProductDto createProduct() {
        return ProductDto.builder()
                .productId(1L)
                .productCategoryId(1L)
                .productName("TubeLight")
                .productPrice(1500.0)
                .productDescription("This electronic tube-light")
                .build();
    }

    public static ProductDto createProductTwo() {
        return ProductDto.builder()
                .productId(2L)
                .productCategoryId(1L)
                .productName("Sprace")
                .productPrice(800.0)
                .productDescription("This electronic tube-light")
                .build();
    }
    public static EditProductDto getProductById() throws IOException {
        return EditProductDto.builder()
                .productId(1L)
                .productCategoryId(1L)
                .productName("TubeLight")
                .productPrice(1500.0)
                .productDescription("This electronic tube-light")
                .build();
    }
    public static List<ViewProductDto> getProductList(Boolean isActive) {
        List<ViewProductDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewProductDto(1,1L,"TubeLight",1500.0,"Electronic","Enabled"));
        fetchList.add(new ViewProductDto(2,2L,"Sprace",800.0,"Electronic",isActive?"Enabled":"Disabled"));
        return fetchList;
    }
}
