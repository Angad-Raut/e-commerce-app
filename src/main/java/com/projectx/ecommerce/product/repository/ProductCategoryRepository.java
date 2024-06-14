package com.projectx.ecommerce.product.repository;

import com.projectx.ecommerce.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
   ProductCategory findByCategoryId(Long categoryId);
   Boolean existsByCategoryName(String categoryName);

   @Transactional
   @Modifying
   @Query(value = "update product_category set category_status=:status where category_id=:categoryId",nativeQuery = true)
   Integer updateCategoryStatus(@Param("categoryId")Long categoryId,@Param("status")Boolean status);

   @Query(value = "select * from product_category where category_status=:status",nativeQuery = true)
   List<ProductCategory> getAllEnabledCategories(@Param("status")Boolean status);

    @Query(value = "select category_id,category_name from product_category where category_status=:status",nativeQuery = true)
    List<Object[]> getCategoryDropDown(@Param("status")Boolean status);
}
