package com.projectx.ecommerce.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.order.payloads.PaymentRequestDto;

import java.util.*;

public class PaymentTestData {

    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static PaymentRequestDto doPayment() {
        return PaymentRequestDto.builder()
                .paymentId(1L)
                .paymentType(MessageUtils.NFT_TYPE)
                .accountHolderName("Angad Kantilal Raut")
                .accountNumber("274300010826")
                .bankName("ICICI Bank")
                .payableAmount(2000.0)
                .ifsCode("ICIC00002743")
                .remark("Payment Done")
                .build();
    }
    public static PaymentDetails getPaymentDetailsById() {
        PaymentDetails paymentDetails = new PaymentDetails();
        Set<PaymentHistory> paymentHistories = new HashSet<>();
        paymentHistories.add(toPaymentHistory(paymentDetails));
        return PaymentDetails.builder()
                .paymentHistories(paymentHistories)
                .orderAmount(4600.0)
                .paymentStatus(MessageUtils.UNPAID_STATUS)
                .paymentAmount(2600.0)
                .paymentDate(new Date())
                .paymentId(1L)
                .build();
    }
    protected static PaymentHistory toPaymentHistory(PaymentDetails paymentDetails) {
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
    public static List<PaymentHistory> getPaymentHistory() {
        Set<PaymentHistory> paymentHistories = new HashSet<>();
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .paymentDate(new Date())
                .paymentStatus(MessageUtils.UNPAID_STATUS)
                .paymentId(1L)
                .paymentAmount(4600.0)
                .orderAmount(4600.0)
                .paymentHistories(paymentHistories)
                .build();
        List<PaymentHistory> fetchList = new ArrayList<>();
        PaymentHistory paymentHistory = PaymentHistory.builder()
                .paymentType(MessageUtils.NFT_TYPE)
                .paymentTime(new Date())
                .ifsCode("IFC00002434")
                .bankName("ICICI Bank")
                .payableAmount(2600.0)
                .accountNumber("2743000010826")
                .accountHolderName("Angad Kantilal Raut")
                .remark("Payment Done")
                .paymentDetails(paymentDetails)
                .build();
        PaymentHistory paymentHistoryTwo = PaymentHistory.builder()
                .paymentType(MessageUtils.NFT_TYPE)
                .paymentTime(new Date())
                .ifsCode("IFC00002434")
                .bankName("ICICI Bank")
                .payableAmount(2000.0)
                .accountNumber("2743000010826")
                .accountHolderName("Angad Kantilal Raut")
                .remark("Payment Done")
                .paymentDetails(paymentDetails)
                .build();
        fetchList.add(paymentHistory);
        fetchList.add(paymentHistoryTwo);
        return fetchList;
    }
}
