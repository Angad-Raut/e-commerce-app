package com.projectx.ecommerce.inventory.repository;

import com.projectx.ecommerce.inventory.entity.BatchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BatchDetailsRepository extends JpaRepository<BatchDetails,Long> {
    BatchDetails findByBatchId(Long batchId);
    Boolean existsByBatchName(String batchName);
    @Transactional
    @Modifying
    @Query(value = "update batch_details set batch_status=:status where batch_id=:batchId",nativeQuery = true)
    Integer updateBatchStatus(@Param("batchId")Long batchId,@Param("status")Boolean status);
    @Transactional
    @Modifying
    @Query(value = "update batch_details set remaining_qty=:quantity where batch_id=:batchId and batch_status=:status and "
            +"product_category_category_id=:categoryId and product_details_product_id=:productId",nativeQuery = true)
    Integer updateBatchQuantity(@Param("batchId")Long batchId,@Param("quantity")Integer quantity,
                                @Param("categoryId")Long categoryId,@Param("productId")Long productId,
                                @Param("status")Boolean status);

    @Query(value = "select remaining_qty from batch_details where batch_id=:batchId and batch_status=:status and "
            +"product_category_category_id=:categoryId and product_details_product_id=:productId",nativeQuery = true)
    Integer getRemainingQty(@Param("categoryId")Long categoryId,@Param("productId")Long productId,
                            @Param("batchId")Long batchId,@Param("status")Boolean status);

    @Query(value = "select batch_id,batch_name,remaining_qty from batch_details where batch_status=:status and "
            +"product_category_category_id=:categoryId and product_details_product_id=:productId",nativeQuery = true)
    List<Object[]> getBatchDropDown(@Param("categoryId")Long categoryId,@Param("productId")Long productId,
                                    @Param("status")Boolean status);

    @Query(value = "select * from batch_details where batch_status=:status and "
            +"product_category_category_id=:categoryId and product_details_product_id=:productId",nativeQuery = true)
    List<BatchDetails> getAllBatchListWithCategoryAndProduct(@Param("categoryId")Long categoryId,@Param("productId")Long productId,
                                    @Param("status")Boolean status);

    @Query(value = "select * from batch_details where batch_status=:status and "
            +"product_category_category_id=:categoryId",nativeQuery = true)
    List<BatchDetails> getAllBatchListWithCategory(@Param("categoryId")Long categoryId,@Param("status")Boolean status);

    @Query(value = "select * from batch_details where batch_status=:status",nativeQuery = true)
    List<BatchDetails> getAllBatchByStatus(@Param("status")Boolean status);
}
