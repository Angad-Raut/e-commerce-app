package com.projectx.ecommerce.customer.repository;

import com.projectx.ecommerce.customer.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDetails,Long> {
    CustomerDetails findByCustomerId(Long customerId);
    Boolean existsByCustomerMobile(Long customerMobile);
    Boolean existsByCustomerEmail(String customerEmail);

    CustomerDetails findByCustomerMobile(Long mobile);
    CustomerDetails findByCustomerEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update customer_details set customer_status=:status where customer_id=:customerId",nativeQuery = true)
    Integer updateCustomerStatus(@Param("customerId")Long customerId,@Param("status")Boolean status);

    @Query(value = "select c.customer_id,c.customer_name from customer_details c where c.customer_status=:status",nativeQuery = true)
    List<Object[]> getCustomerDropDown(@Param("status")Boolean status);

    @Query(value = "select * from customer_details where customer_status=:status",nativeQuery = true)
    List<CustomerDetails> getAllEnabledCustomers(@Param("status")Boolean status);

    @Query(value = "select count(*) from customer_details",nativeQuery = true)
    Integer getCustomerCount();
}
