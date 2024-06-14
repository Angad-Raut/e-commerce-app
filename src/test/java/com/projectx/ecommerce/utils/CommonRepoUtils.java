package com.projectx.ecommerce.utils;

import com.projectx.ecommerce.authentication.payloads.LoginRequestDto;
import com.projectx.ecommerce.authentication.payloads.LoginResponseDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.inventory.entity.BatchDetails;
import com.projectx.ecommerce.inventory.entity.InventoryStockDetails;
import com.projectx.ecommerce.order.entity.OrderDetails;
import com.projectx.ecommerce.order.entity.OrderItem;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.entity.ProductDetails;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CommonRepoUtils {

      public static ProductCategory toCategory() {
          return  ProductCategory.builder()
                  .categoryName("Electronic")
                  .categoryStatus(true)
                  .insertedTime(new Date())
                  .updatedTime(new Date())
                  .build();
      }

      public static ProductCategory toCategoryTwo() {
          return ProductCategory.builder()
                  .categoryName("Food")
                  .categoryStatus(true)
                  .insertedTime(new Date())
                  .updatedTime(new Date())
                  .build();
      }

      public static ProductDetails toProduct(ProductCategory category) {
          return ProductDetails.builder()
                  .productName("TubeLight")
                  .productStatus(MessageUtils.ADDED_STATUS)
                  .productPrice(1500.0)
                  .productDescription("This is electronic product")
                  .insertedTime(new Date())
                  .updatedTime(new Date())
                  .productCategory(category)
                  .build();
      }

      public static ProductDetails toProductTwo(ProductCategory category) {
          return ProductDetails.builder()
                  .productName("Blab")
                  .productStatus(MessageUtils.ADDED_STATUS)
                  .productPrice(200.0)
                  .productDescription("This is electronic product")
                  .insertedTime(new Date())
                  .updatedTime(new Date())
                  .productCategory(category)
                  .build();
      }

      public static BatchDetails toBatch(ProductCategory category,ProductDetails productDetails) {
          return BatchDetails.builder()
                  .productDetails(productDetails)
                  .productCategory(category)
                  .batchName("BH10001")
                  .batchQty(10)
                  .remainingQty(10)
                  .batchStatus(true)
                  .insertedTime(new Date())
                  .updatedTime(new Date())
                  .build();
      }

    public static BatchDetails toBatchTwo(ProductCategory category,ProductDetails productDetails) {
        return BatchDetails.builder()
                .productDetails(productDetails)
                .productCategory(category)
                .batchName("BH10002")
                .batchQty(15)
                .remainingQty(10)
                .batchStatus(true)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .build();
    }
    public static InventoryStockDetails toStockOne(
            ProductCategory category,ProductDetails productDetails,BatchDetails batchDetails) {
          return InventoryStockDetails.builder()
                  .serialNumber("SR10001")
                  .status(true)
                  .productCategory(category)
                  .productDetails(productDetails)
                  .batchDetails(batchDetails)
                  .insertedTime(new Date())
                  .updatedTime(new Date())
                  .build();
    }

    public static InventoryStockDetails toStockTwo(
            ProductCategory category,ProductDetails productDetails,BatchDetails batchDetails) {
        return InventoryStockDetails.builder()
                .serialNumber("SR10002")
                .status(true)
                .productCategory(category)
                .productDetails(productDetails)
                .batchDetails(batchDetails)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .build();
    }

    public static OrderDetails toOrder(OrderTestRequest request) {
        Set<OrderItem> orderItems = new HashSet<>();
        Double totalPrice = (request.getPrice()*request.getQuantity());
        orderItems.add(OrderItem.builder()
                        .itemId(request.getProductId())
                        .quantity(request.getQuantity())
                        .price(request.getPrice())
                        .totalPrice(totalPrice)
                        .build());
          return OrderDetails.builder()
                  .orderNumber(MessageUtils.ORDER_NUMBER)
                  .orderDate(new Date())
                  .orderStatus(true)
                  .customerDetails(request.getCustomerDetails())
                  .paymentDetails(request.getPaymentDetails())
                  .orderItems(orderItems)
                  .orderAmount(totalPrice)
                  .build();
    }
    public static CustomerDetails toCustomer() {
          return CustomerDetails.builder()
                  .customerStatus(true)
                  .customerName("Angad Raut")
                  .customerEmail("angadraut@gmail.com")
                  .customerMobile(9766945760L)
                  .customerAddress("Pune , Maharashtra")
                  .isAdmin(true)
                  .insertedTime(new Date())
                  .updatedTime(new Date())
                  .build();
    }
    public static PaymentDetails toPayment() {
          Set<PaymentHistory> paymentHistories=new HashSet<>();
          return PaymentDetails.builder()
                  .paymentAmount(0.0)
                  .orderAmount(3000.0)
                  .paymentStatus(MessageUtils.UNPAID_STATUS)
                  .paymentDate(new Date())
                  .paymentHistories(paymentHistories)
                  .build();
    }

    public static PaymentHistory toPaymentHistoryOne(PaymentDetails paymentDetails) {
        paymentDetails.setPaymentStatus(MessageUtils.PARTIALLY_PAID);
        paymentDetails.setPaymentAmount(1000.0);
        paymentDetails.setPaymentDate(new Date());
        return PaymentHistory.builder()
                .paymentType(MessageUtils.NFT_TYPE)
                .paymentTime(new Date())
                .paymentDetails(paymentDetails)
                .accountHolderName("Angad Raut")
                .payableAmount(1000.0)
                .accountNumber("274301000826")
                .ifsCode("ICICI00002743")
                .remark("Payment Done")
                .bankName("ICICI Bank")
                .build();
    }

    public static PaymentHistory toPaymentHistoryTwo(PaymentDetails paymentDetails) {
        paymentDetails.setPaymentStatus(MessageUtils.FULL_PAID);
        paymentDetails.setPaymentAmount(3000.0);
        paymentDetails.setPaymentDate(new Date());
        return PaymentHistory.builder()
                .paymentType(MessageUtils.NFT_TYPE)
                .paymentTime(new Date())
                .paymentDetails(paymentDetails)
                .accountHolderName("Angad Raut")
                .payableAmount(2000.0)
                .accountNumber("274301000826")
                .ifsCode("ICICI00002743")
                .remark("Payment Done")
                .bankName("ICICI Bank")
                .build();
    }

    public static LoginRequestDto loginRequest() {
          return LoginRequestDto.builder()
                  .username("9766945760")
                  .password("angad@75")
                  .build();
    }

    public static LoginResponseDto loginResponse() {
          return LoginResponseDto.builder()
                  .isAdmin(true)
                  .userEmail("angadraut@gmail.com")
                  .userMobile(9766945760L)
                  .userName("Angad Raut")
                  .build();
    }
}
