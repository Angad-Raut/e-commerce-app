package com.projectx.ecommerce.inventory.repository;

import com.projectx.ecommerce.inventory.entity.InventoryStockDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface InventoryStockRepository extends JpaRepository<InventoryStockDetails,Long> {
    InventoryStockDetails findByStockId(Long stockId);
    Boolean existsBySerialNumber(String serialNumber);
    @Transactional
    @Modifying
    @Query(value = "update inventory_stock_details set status=:status where stock_id=:stockId",nativeQuery = true)
    Integer updateStatus(@Param("stockId")Long stockId,@Param("status")Boolean status);

    @Transactional
    @Modifying
    @Query(value = "update inventory_stock_details set status=:status where stock_id in (:stockIdList)",nativeQuery = true)
    Integer updateAllStatus(@Param("stockIdList")List<Long> stockIdList,@Param("status")Boolean status);

    @Query(value = "select * from inventory_stock_details where status=:status",nativeQuery = true)
    List<InventoryStockDetails> getAllInventoryStockByStatus(@Param("status")Boolean status);

    @Query(value = "select * from inventory_stock_details where status=:status and "
            +"product_category_category_id=:categoryId and product_details_product_id=:productId and "
            +"batch_details_batch_id=:batchId",nativeQuery = true)
    List<InventoryStockDetails> getAllInventoryStockByCategoryAndProductAndBatch(@Param("status")Boolean status,
            @Param("categoryId")Long categoryId,@Param("productId")Long productId,@Param("batchId")Long batchId);

    @Query(value = "select * from inventory_stock_details where status=:status and "
            +"product_category_category_id=:categoryId and product_details_product_id=:productId",nativeQuery = true)
    List<InventoryStockDetails> getAllInventoryStockByCategoryAndProduct(@Param("status")Boolean status,
            @Param("categoryId")Long categoryId,@Param("productId")Long productId);

    @Query(value = "select * from inventory_stock_details where status=:status and "
            +"product_category_category_id=:categoryId",nativeQuery = true)
    List<InventoryStockDetails> getAllInventoryStockByCategory(
            @Param("status")Boolean status, @Param("categoryId")Long categoryId);
}
