package com.projectx.ecommerce.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.order.entity.OrderDetails;
import com.projectx.ecommerce.order.entity.OrderItem;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.order.payloads.CustomersOrdersDto;
import com.projectx.ecommerce.order.payloads.ItemsDto;
import com.projectx.ecommerce.order.payloads.OrderDto;
import com.projectx.ecommerce.order.payloads.ViewOrdersDto;

import java.util.*;

public class OrderTestData {

    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static OrderDto placeOrder() {
        return OrderDto.builder()
                .customerId(1L)
                .itemsDto(setItems())
                .build();
    }
    private static Set<ItemsDto> setItems() {
        Set<ItemsDto> itemsDtoList = new HashSet<>();
        itemsDtoList.add(new ItemsDto(1L,1500.0,2));
        itemsDtoList.add(new ItemsDto(2L,800.0,2));
        return itemsDtoList;
    }
    public static OrderDetails setOrderDetails() {
        Set<PaymentHistory> paymentHistories = new HashSet<>();
        paymentHistories.add(toPaymentHistory(new PaymentDetails()));
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .paymentId(1L)
                .paymentAmount(4600.0)
                .paymentStatus(MessageUtils.UNPAID_STATUS)
                .paymentDate(new Date())
                .orderAmount(4600.0)
                .paymentHistories(paymentHistories)
                .build();
        CustomerDetails customerDetails = CustomerDetails.builder()
                .customerId(1L)
                .customerStatus(true)
                .updatedTime(new Date())
                .insertedTime(new Date())
                .customerAddress("Pune , Maharashtra")
                .customerEmail("angadraut75@gmail.com")
                .customerName("Angad Kantilal Raut")
                .customerMobile(9766945760L)
                .build();
       return OrderDetails.builder()
                .paymentDetails(paymentDetails)
                .customerDetails(customerDetails)
                .orderId(1L)
                .orderNumber(MessageUtils.ORDER_NUMBER)
                .orderStatus(true)
                .orderAmount(4600.0)
                .orderItems(setItemsEntity())
                .orderDate(new Date())
                .build();
    }

    public static PaymentDetails setPaymentDetails() {
        Set<PaymentHistory> paymentHistories = new HashSet<>();
        return PaymentDetails.builder()
                .paymentId(2L)
                .paymentAmount(4600.0)
                .paymentStatus(MessageUtils.UNPAID_STATUS)
                .paymentDate(new Date())
                .orderAmount(4600.0)
                .paymentHistories(paymentHistories)
                .build();
    }

    public static OrderDetails setOrderDetailsTwo() {
        Set<PaymentHistory> paymentHistories = new HashSet<>();
        paymentHistories.add(toPaymentHistory(new PaymentDetails()));
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .paymentId(2L)
                .paymentAmount(4600.0)
                .paymentStatus(MessageUtils.UNPAID_STATUS)
                .paymentDate(new Date())
                .orderAmount(4600.0)
                .paymentHistories(paymentHistories)
                .build();
        CustomerDetails customerDetails = CustomerDetails.builder()
                .customerId(1L)
                .customerStatus(true)
                .updatedTime(new Date())
                .insertedTime(new Date())
                .customerAddress("Pune , Maharashtra")
                .customerEmail("angadraut75@gmail.com")
                .customerName("Angad Kantilal Raut")
                .customerMobile(9766945760L)
                .build();
        return OrderDetails.builder()
                .paymentDetails(paymentDetails)
                .customerDetails(customerDetails)
                .orderId(2L)
                .orderNumber(MessageUtils.ORDER_NUMBER_TWO)
                .orderStatus(true)
                .orderAmount(4600.0)
                .orderItems(setItemsEntity())
                .orderDate(new Date())
                .build();
    }
    public static List<OrderDetails> toOrderList(Boolean isComplete) {
        List<OrderDetails> fetchList = new ArrayList<>();
        OrderDetails orderDetails = setOrderDetails();
        OrderDetails orderDetails1 = setOrderDetailsTwo();
        if (isComplete) {
            orderDetails1.setOrderStatus(false);
            orderDetails.setOrderStatus(false);
        }
        fetchList.add(orderDetails1);
        fetchList.add(orderDetails);
        return fetchList;
    }
    public static List<OrderDetails> toCustomerOrderList() {
        List<OrderDetails> fetchList = new ArrayList<>();
        OrderDetails orderDetails1 = setOrderDetailsTwo();
        orderDetails1.setOrderStatus(false);
        fetchList.add(orderDetails1);
        fetchList.add(setOrderDetails());
        return fetchList;
    }
    private static PaymentHistory toPaymentHistory(PaymentDetails paymentDetails) {
        return PaymentHistory.builder()
                .paymentType(MessageUtils.NFT_TYPE)
                .paymentTime(new Date())
                .ifsCode("IFC00002434")
                .bankName("ICICI Bank")
                .payableAmount(4600.0)
                .accountNumber("2743000010826")
                .accountHolderName("Angad Kantilal Raut")
                .paymentDetails(paymentDetails)
                .build();
    }
    private static Set<OrderItem> setItemsEntity() {
        Set<OrderItem> itemSet = new HashSet<>();
        itemSet.add(new OrderItem(1L,2,1500.0,3000.0));
        itemSet.add(new OrderItem(2L,2,800.0,1600.0));
        return itemSet;
    }
    public static List<CustomersOrdersDto> getCustomerOrderHistory() {
        List<CustomersOrdersDto> fetchList = new ArrayList<>();
        fetchList.add(new CustomersOrdersDto(1,1L,"100001","03 January 2024",4600.0,"Incomplete"));
        fetchList.add(new CustomersOrdersDto(2,2L,"100002","03 January 2024",4600.0,"Complete"));
        return fetchList;
    }
    public static List<ViewOrdersDto> getAllOrders(Boolean isComplete) {
        List<ViewOrdersDto> fetchList = new ArrayList<>();
        fetchList.add(new ViewOrdersDto(1,1L,1L,"Angad Raut","100001","03 January 2024",4600.0,isComplete?"Completed":"Incomplete"));
        fetchList.add(new ViewOrdersDto(2,2L,1L,"Angad Raut","100002","03 January 2024",4600.0,isComplete?"Completed":"InComplete"));
        return fetchList;
    }
}
