package com.projectx.ecommerce.order.repository;

import com.projectx.ecommerce.order.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails,Long> {
    PaymentDetails findByPaymentId(Long paymentId);

    @Transactional
    @Modifying
    @Query(value = "update payment_details set payment_status=:status where payment_id=:paymentId",nativeQuery = true)
    Integer updatePaymentStatus(@Param("paymentId")Long paymentId,@Param("status")Integer status);
}
