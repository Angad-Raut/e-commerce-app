package com.projectx.ecommerce.repository;

import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.order.entity.PaymentDetails;
import com.projectx.ecommerce.order.entity.PaymentHistory;
import com.projectx.ecommerce.order.repository.PaymentHistoryRepository;
import com.projectx.ecommerce.order.repository.PaymentRepository;
import com.projectx.ecommerce.utils.CommonRepoUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @BeforeEach
    public void clearData() {
        paymentHistoryRepository.deleteAll();
        paymentRepository.deleteAll();
    }

    @Test
    public void findByPaymentIdTest() {
        PaymentDetails details = paymentRepository.save(CommonRepoUtils.toPayment());
        PaymentDetails paymentDetails = paymentRepository.findByPaymentId(details.getPaymentId());
        assertEquals(paymentDetails.getPaymentId(),details.getPaymentId());
        assertEquals(paymentDetails.getPaymentAmount(),details.getPaymentAmount());
        assertEquals(paymentDetails.getOrderAmount(),details.getOrderAmount());
        assertEquals(paymentDetails.getPaymentStatus(),details.getPaymentStatus());
    }

    @Test
    public void updatePaymentStatusTest() {
        PaymentDetails details = paymentRepository.save(CommonRepoUtils.toPayment());
        Integer result = paymentRepository.updatePaymentStatus(details.getPaymentId(), MessageUtils.FULL_PAID);
        assertEquals(result,1);
    }

    @Test
    public void getPaymentHistoryByPaymentIdTest() {
        PaymentDetails details = paymentRepository.save(CommonRepoUtils.toPayment());
        PaymentHistory paymentHistoryOne = paymentHistoryRepository.save(CommonRepoUtils.toPaymentHistoryOne(details));
        PaymentHistory paymentHistoryTwo = paymentHistoryRepository.save(CommonRepoUtils.toPaymentHistoryTwo(details));
        List<PaymentHistory> fetchList = paymentHistoryRepository.getPaymentHistoryByPaymentId(details.getPaymentId());
        assertEquals(fetchList.get(0).getPaymentDetails().getPaymentId(),paymentHistoryOne.getPaymentDetails().getPaymentId());
        assertEquals(fetchList.get(0).getPaymentDetails().getOrderAmount(),paymentHistoryOne.getPaymentDetails().getOrderAmount());
        assertEquals(fetchList.get(0).getPaymentType(),paymentHistoryOne.getPaymentType());
        assertEquals(fetchList.get(0).getPayableAmount(),paymentHistoryOne.getPayableAmount());
        assertEquals(fetchList.get(0).getAccountHolderName(),paymentHistoryOne.getAccountHolderName());
        assertEquals(fetchList.get(0).getAccountNumber(),paymentHistoryOne.getAccountNumber());
        assertEquals(fetchList.get(0).getIfsCode(),paymentHistoryOne.getIfsCode());
        assertEquals(fetchList.get(1).getPaymentDetails().getPaymentId(),paymentHistoryTwo.getPaymentDetails().getPaymentId());
        assertEquals(fetchList.get(1).getPaymentDetails().getOrderAmount(),paymentHistoryTwo.getPaymentDetails().getOrderAmount());
        assertEquals(fetchList.get(1).getPaymentType(),paymentHistoryTwo.getPaymentType());
        assertEquals(fetchList.get(1).getPayableAmount(),paymentHistoryTwo.getPayableAmount());
        assertEquals(fetchList.get(1).getAccountHolderName(),paymentHistoryTwo.getAccountHolderName());
        assertEquals(fetchList.get(1).getAccountNumber(),paymentHistoryTwo.getAccountNumber());
        assertEquals(fetchList.get(1).getIfsCode(),paymentHistoryOne.getIfsCode());
    }
}
