package com.projectx.ecommerce.utils;

import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.customer.payloads.CustomerDto;
import com.projectx.ecommerce.customer.payloads.ViewCustomerDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerTestUtils {

    public static CustomerDto toCustomerDto() {
        return CustomerDto.builder()
                .customerName("Angad Raut")
                .customerMobile(9766945760L)
                .customerEmail("angadraut75@gmail.com")
                .customerAddress("Pune ,Maharashtra")
                .build();
    }
    public static CustomerDetails toCustomerEntity() {
        return CustomerDetails.builder()
                .customerId(1L)
                .customerName("Angad Raut")
                .customerMobile(9766945760L)
                .customerEmail("angadraut75@gmail.com")
                .customerAddress("Pune ,Maharashtra")
                .customerStatus(true)
                .updatedTime(new Date())
                .insertedTime(new Date())
                .build();
    }
    public static CustomerDetails toCustomerEntityTwo() {
        return CustomerDetails.builder()
                .customerId(2L)
                .customerName("Shital Raut")
                .customerMobile(9766945761L)
                .customerEmail("shitalraut95@gmail.com")
                .customerAddress("Pune ,Maharashtra")
                .customerStatus(true)
                .updatedTime(new Date())
                .insertedTime(new Date())
                .build();
    }
    public static List<CustomerDetails> customerDetailsList() {
        List<CustomerDetails> fetchList = new ArrayList<>();
        fetchList.add(toCustomerEntity());
        fetchList.add(toCustomerEntityTwo());
        return fetchList;
    }
    public static List<ViewCustomerDto> getAllCustomersList() {
        List<ViewCustomerDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewCustomerDto(1,1L,"Angad Raut","angadraut75@gmail.com",9766945760L,"Enabled"));
        fetchList.add(new ViewCustomerDto(2,2L,"Shital Raut","shitalraut95@gmail.com",9766945761L,"Enabled"));
        return fetchList;
    }
    public static List<EntityIdAndValueDto> dropDown() {
        List<EntityIdAndValueDto> fetchList = new ArrayList<>();
        fetchList.add(new EntityIdAndValueDto(1L,"Angad Raut"));
        fetchList.add(new EntityIdAndValueDto(2L,"Shital Raut"));
        return fetchList;
    }
    public static List<Object[]> objectList() {
        List<Object[]> data = new ArrayList<>();
        Object[] objFirst = new Object[] { 1L,"Angad Raut"};
        Object[] objSecond = new Object[] { 2L,"Shital Raut"};
        data.add(objFirst);
        data.add(objSecond);
        return data;
    }
}
