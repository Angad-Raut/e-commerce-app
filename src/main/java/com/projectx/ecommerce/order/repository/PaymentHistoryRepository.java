package com.projectx.ecommerce.order.repository;

import com.projectx.ecommerce.order.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory,Long> {

    @Query(value = "select * from payment_history where payment_details_payment_id=:paymentId",nativeQuery = true)
    List<PaymentHistory> getPaymentHistoryByPaymentId(@Param("paymentId")Long paymentId);
}
