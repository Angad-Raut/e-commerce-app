package com.projectx.ecommerce.order.repository;

import com.projectx.ecommerce.order.entity.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Long> {
    OrderDetails findByOrderId(Long orderId);

    @Query(value = "select order_number from order_details order by order_id desc limit 1",nativeQuery = true)
    String getLastOrderNumber();

    @Transactional
    @Modifying
    @Query(value = "update order_details set order_status=:status where order_id=:orderId",nativeQuery = true)
    Integer updateOrderStatus(@Param("orderId")Long orderId,@Param("status")Boolean status);

    @Query(value = "select * from order_details where order_status=:status",nativeQuery = true)
    List<OrderDetails> getAllOrdersByStatus(@Param("status")Boolean status);
    @Query(value = "select * from order_details where customer_details_customer_id=:customerId",nativeQuery = true)
    List<OrderDetails> getCustomerOrderHistory(@Param("customerId")Long customerId);

    @Query(value = "select payment_details_payment_id as pid from order_details where order_id=:orderId",nativeQuery = true)
    Long getPaymentIdByOrderId(@Param("orderId")Long orderId);

    @Query(value = "select o.order_id,o.order_number,o.order_date,o.order_amount, "
            +"p.payment_id,p.payment_amount,c.customer_id,c.customer_name from order_details o "
            +"join payment_details p on o.payment_details_payment_id=p.payment_id "
            +"join customer_details c on o.customer_details_customer_id=c.customer_id "
            +"where p.payment_status in(:statusList)",nativeQuery = true)
    List<Object[]> getOrdersByPaymentStatus(@Param("statusList")List<Integer> statusList);
}
