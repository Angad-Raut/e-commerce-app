package com.projectx.ecommerce.service;

import com.projectx.ecommerce.common.payloads.EntityIdAndTypeDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.utils.PaymentTestData;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.order.repository.OrderRepository;
import com.projectx.ecommerce.order.repository.PaymentHistoryRepository;
import com.projectx.ecommerce.order.repository.PaymentRepository;
import com.projectx.ecommerce.order.service.OrderService;
import com.projectx.ecommerce.order.service.PaymentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentServiceTest extends PaymentTestData {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentHistoryRepository paymentHistoryRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    public void do_payment_test() {
        when(paymentRepository.findByPaymentId(1L)).thenReturn(getPaymentDetailsById());
        PaymentDetails paymentDetails = getPaymentDetailsById();
        when(paymentHistoryRepository.save(toPaymentHistory(paymentDetails))).thenReturn(toPaymentHistory(paymentDetails));
        when(paymentRepository.save(getPaymentDetailsById())).thenReturn(getPaymentDetailsById());
        Boolean status = paymentService.doPayment(doPayment());
        assertEquals(status,true);
    }

    @Test
    public void get_payment_details_by_payment_id_test() {
        when(paymentRepository.findByPaymentId(1L)).thenReturn(getPaymentDetailsById());
        PaymentDetails paymentDetails = paymentService.getPaymentDetailsByPaymentId(new EntityIdDto(1L));
        assertEquals(paymentDetails.getPaymentId(),getPaymentDetailsById().getPaymentId());
        assertEquals(paymentDetails.getPaymentAmount(),getPaymentDetailsById().getPaymentAmount());
        assertEquals(paymentDetails.getOrderAmount(),getPaymentDetailsById().getOrderAmount());
    }

    @Test
    public void get_payment_details_by_order_id_test() {
        when(orderRepository.getPaymentIdByOrderId(1L)).thenReturn(1L);
        when(orderService.getPaymentIdByOrderId(new EntityIdDto(1L))).thenReturn(1L);
        when(paymentRepository.findByPaymentId(1L)).thenReturn(getPaymentDetailsById());
        PaymentDetails paymentDetails = paymentService.getPaymentDetailsByOrderId(new EntityIdDto(1L));
        assertEquals(paymentDetails.getPaymentId(),getPaymentDetailsById().getPaymentId());
        assertEquals(paymentDetails.getPaymentAmount(),getPaymentDetailsById().getPaymentAmount());
        assertEquals(paymentDetails.getOrderAmount(),getPaymentDetailsById().getOrderAmount());
    }

    @Test
    public void update_payment_status_test() {
        when(paymentRepository.findByPaymentId(1L)).thenReturn(getPaymentDetailsById());
        when(paymentRepository.updatePaymentStatus(1L,3)).thenReturn(1);
        Boolean status = paymentService.updatePaymentStatus(new EntityIdAndTypeDto(1L,3));
        assertEquals(status,true);
    }

    @Test
    public void get_payment_history_by_payment_id_test() {
        when(paymentHistoryRepository.getPaymentHistoryByPaymentId(1L)).thenReturn(getPaymentHistory());
        List<PaymentHistory> paymentHistories = paymentService.getPaymentHistoryByPaymentId(new EntityIdDto(1L));
        assertEquals(paymentHistories.get(0).getPayableAmount(),getPaymentHistory().get(0).getPayableAmount());
        assertEquals(paymentHistories.get(0).getPaymentType(),getPaymentHistory().get(0).getPaymentType());
        assertEquals(paymentHistories.get(0).getRemark(),getPaymentHistory().get(0).getRemark());
        assertEquals(paymentHistories.get(1).getPayableAmount(),getPaymentHistory().get(1).getPayableAmount());
        assertEquals(paymentHistories.get(1).getPaymentType(),getPaymentHistory().get(1).getPaymentType());
        assertEquals(paymentHistories.get(1).getRemark(),getPaymentHistory().get(1).getRemark());
    }

    @Test
    public void get_payment_history_by_order_id_test() {
        when(orderRepository.getPaymentIdByOrderId(1L)).thenReturn(1L);
        when(orderService.getPaymentIdByOrderId(new EntityIdDto(1L))).thenReturn(1L);
        when(paymentHistoryRepository.getPaymentHistoryByPaymentId(1L)).thenReturn(getPaymentHistory());
        List<PaymentHistory> paymentHistories = paymentService.getPaymentHistoryByOrderId(new EntityIdDto(1L));
        assertEquals(paymentHistories.get(0).getPayableAmount(),getPaymentHistory().get(0).getPayableAmount());
        assertEquals(paymentHistories.get(0).getPaymentType(),getPaymentHistory().get(0).getPaymentType());
        assertEquals(paymentHistories.get(0).getRemark(),getPaymentHistory().get(0).getRemark());
        assertEquals(paymentHistories.get(1).getPayableAmount(),getPaymentHistory().get(1).getPayableAmount());
        assertEquals(paymentHistories.get(1).getPaymentType(),getPaymentHistory().get(1).getPaymentType());
        assertEquals(paymentHistories.get(1).getRemark(),getPaymentHistory().get(1).getRemark());
    }
}
