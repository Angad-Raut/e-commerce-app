package com.projectx.ecommerce.setting.repository;

import com.projectx.ecommerce.setting.entity.DiscountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountDetails,Long> {
    @Query(value = "select * from discount_details where id=:id",nativeQuery = true)
    DiscountDetails getById(@Param("id")Long id);
    Boolean existsByDiscountTypeAndDiscount(Integer discountType,Double discount);

    @Query(value = "select discount_type,discount from discount_details where status=:status",nativeQuery = true)
    List<Object[]> getDiscountsByStatus(@Param("status")Boolean status);

    @Transactional
    @Modifying
    @Query(value = "update discount_details set status=:status where id=:id",nativeQuery = true)
    Integer updateStatus(@Param("id")Long id,@Param("status")Boolean status);
}
