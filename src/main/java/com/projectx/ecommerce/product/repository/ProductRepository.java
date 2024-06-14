package com.projectx.ecommerce.product.repository;

import com.projectx.ecommerce.product.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductDetails,Long> {
    ProductDetails findByProductId(Long productId);
    Boolean existsByProductName(String productName);
    @Transactional
    @Modifying
    @Query(value = "update product_details set product_status=:status where product_id=:productId",nativeQuery = true)
    Integer updateProductStatus(@Param("productId")Long productId,@Param("status")Integer status);

    @Query(value = "select * from product_details where product_status=:status",nativeQuery = true)
    List<ProductDetails> getAllProductsByStatus(@Param("status")Integer status);
    @Query(value = "select * from product_details where product_category_category_id=:categoryId and product_status=:status",nativeQuery = true)
    List<ProductDetails> getAllVerifiedProductsByCategory(@Param("status")Integer status,@Param("categoryId")Long categoryId);
}
