package com.projectx.ecommerce.repository;

import com.projectx.ecommerce.inventory.entity.BatchDetails;
import com.projectx.ecommerce.inventory.entity.InventoryStockDetails;
import com.projectx.ecommerce.inventory.repository.BatchDetailsRepository;
import com.projectx.ecommerce.inventory.repository.InventoryStockRepository;
import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.entity.ProductDetails;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import com.projectx.ecommerce.product.repository.ProductRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class InventoryStockRepositoryTest {

    @Autowired
    private InventoryStockRepository inventoryStockRepository;

    @Autowired
    private BatchDetailsRepository batchDetailsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    public void clearData() {
        inventoryStockRepository.deleteAll();
        batchDetailsRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    public List<InventoryStockDetails> toStockList() {
        ProductCategory category = productCategoryRepository.save(CommonRepoUtils.toCategory());
        ProductDetails productDetails = productRepository.save(CommonRepoUtils.toProduct(category));
        BatchDetails batchDetails = batchDetailsRepository.save(CommonRepoUtils.toBatch(category,productDetails));
        inventoryStockRepository.save(CommonRepoUtils.toStockOne(category,productDetails,batchDetails));
        inventoryStockRepository.save(CommonRepoUtils.toStockTwo(category,productDetails,batchDetails));
        return inventoryStockRepository.findAll();
    }

    @Test
    public void findByStockIdTest() {
        InventoryStockDetails details = toStockList().get(0);
        InventoryStockDetails stock = inventoryStockRepository.findByStockId(details.getStockId());
        assertEquals(stock.getStockId(),details.getStockId());
        assertEquals(stock.getSerialNumber(),details.getSerialNumber());
        assertEquals(stock.getStatus(),details.getStatus());
        assertEquals(stock.getBatchDetails().getBatchName(),details.getBatchDetails().getBatchName());
        assertEquals(stock.getProductDetails().getProductName(),details.getProductDetails().getProductName());
        assertEquals(stock.getProductCategory().getCategoryName(),details.getProductCategory().getCategoryName());
    }

    @Test
    public void existsBySerialNumberTest() {
        InventoryStockDetails details = toStockList().get(0);
        Boolean status = inventoryStockRepository.existsBySerialNumber(details.getSerialNumber());
        assertTrue(status);
    }

    @Test
    public void updateStatusTest() {
        InventoryStockDetails details = toStockList().get(0);
        Integer result = inventoryStockRepository.updateStatus(details.getStockId(),false);
        assertEquals(result,1);
    }

    @Test
    public void updateAllStatusTest() {
        InventoryStockDetails detailsOne = toStockList().get(0);
        InventoryStockDetails detailsTwo = toStockList().get(1);
        List<Long> idList = new ArrayList<>();
        idList.add(detailsOne.getStockId());
        idList.add(detailsTwo.getStockId());
        Integer result = inventoryStockRepository.updateAllStatus(idList,false);
        assertEquals(result,2);
    }

    @Test
    public void getAllInventoryStockByStatusTest() {
        InventoryStockDetails detailsOne = toStockList().get(0);
        InventoryStockDetails detailsTwo = toStockList().get(1);
        List<InventoryStockDetails> fetchList = inventoryStockRepository.getAllInventoryStockByStatus(true);
        assertEquals(fetchList.get(0).getStockId(),detailsOne.getStockId());
        assertEquals(fetchList.get(0).getSerialNumber(),detailsOne.getSerialNumber());
        assertEquals(fetchList.get(0).getStatus(),detailsOne.getStatus());
        assertEquals(fetchList.get(0).getBatchDetails().getBatchName(),detailsOne.getBatchDetails().getBatchName());
        assertEquals(fetchList.get(0).getProductDetails().getProductName(),detailsOne.getProductDetails().getProductName());
        assertEquals(fetchList.get(0).getProductCategory().getCategoryName(),detailsOne.getProductCategory().getCategoryName());
        assertEquals(fetchList.get(1).getStockId(),detailsTwo.getStockId());
        assertEquals(fetchList.get(1).getSerialNumber(),detailsTwo.getSerialNumber());
        assertEquals(fetchList.get(1).getStatus(),detailsTwo.getStatus());
        assertEquals(fetchList.get(1).getBatchDetails().getBatchName(),detailsTwo.getBatchDetails().getBatchName());
        assertEquals(fetchList.get(1).getProductDetails().getProductName(),detailsTwo.getProductDetails().getProductName());
        assertEquals(fetchList.get(1).getProductCategory().getCategoryName(),detailsTwo.getProductCategory().getCategoryName());
    }

    @Test
    public void getAllInventoryStockByCategoryAndProductAndBatchTest() {
        InventoryStockDetails detailsOne = toStockList().get(0);
        InventoryStockDetails detailsTwo = toStockList().get(1);
        List<InventoryStockDetails> fetchList = inventoryStockRepository.
                getAllInventoryStockByCategoryAndProductAndBatch(true,
                        detailsOne.getProductCategory().getCategoryId(),
                        detailsOne.getProductDetails().getProductId(),
                        detailsOne.getBatchDetails().getBatchId());
        assertEquals(fetchList.get(0).getStockId(),detailsOne.getStockId());
        assertEquals(fetchList.get(0).getSerialNumber(),detailsOne.getSerialNumber());
        assertEquals(fetchList.get(0).getStatus(),detailsOne.getStatus());
        assertEquals(fetchList.get(0).getBatchDetails().getBatchName(),detailsOne.getBatchDetails().getBatchName());
        assertEquals(fetchList.get(0).getProductDetails().getProductName(),detailsOne.getProductDetails().getProductName());
        assertEquals(fetchList.get(0).getProductCategory().getCategoryName(),detailsOne.getProductCategory().getCategoryName());
        assertEquals(fetchList.get(1).getStockId(),detailsTwo.getStockId());
        assertEquals(fetchList.get(1).getSerialNumber(),detailsTwo.getSerialNumber());
        assertEquals(fetchList.get(1).getStatus(),detailsTwo.getStatus());
        assertEquals(fetchList.get(1).getBatchDetails().getBatchName(),detailsTwo.getBatchDetails().getBatchName());
        assertEquals(fetchList.get(1).getProductDetails().getProductName(),detailsTwo.getProductDetails().getProductName());
        assertEquals(fetchList.get(1).getProductCategory().getCategoryName(),detailsTwo.getProductCategory().getCategoryName());
    }

    @Test
    public void getAllInventoryStockByCategoryAndProductTest() {
        InventoryStockDetails detailsOne = toStockList().get(0);
        InventoryStockDetails detailsTwo = toStockList().get(1);
        List<InventoryStockDetails> fetchList = inventoryStockRepository.
                getAllInventoryStockByCategoryAndProduct(true,
                        detailsOne.getProductCategory().getCategoryId(),
                        detailsOne.getProductDetails().getProductId());
        assertEquals(fetchList.get(0).getStockId(),detailsOne.getStockId());
        assertEquals(fetchList.get(0).getSerialNumber(),detailsOne.getSerialNumber());
        assertEquals(fetchList.get(0).getStatus(),detailsOne.getStatus());
        assertEquals(fetchList.get(0).getBatchDetails().getBatchName(),detailsOne.getBatchDetails().getBatchName());
        assertEquals(fetchList.get(0).getProductDetails().getProductName(),detailsOne.getProductDetails().getProductName());
        assertEquals(fetchList.get(0).getProductCategory().getCategoryName(),detailsOne.getProductCategory().getCategoryName());
        assertEquals(fetchList.get(1).getStockId(),detailsTwo.getStockId());
        assertEquals(fetchList.get(1).getSerialNumber(),detailsTwo.getSerialNumber());
        assertEquals(fetchList.get(1).getStatus(),detailsTwo.getStatus());
        assertEquals(fetchList.get(1).getBatchDetails().getBatchName(),detailsTwo.getBatchDetails().getBatchName());
        assertEquals(fetchList.get(1).getProductDetails().getProductName(),detailsTwo.getProductDetails().getProductName());
        assertEquals(fetchList.get(1).getProductCategory().getCategoryName(),detailsTwo.getProductCategory().getCategoryName());
    }

    @Test
    public void getAllInventoryStockByCategoryTest() {
        InventoryStockDetails detailsOne = toStockList().get(0);
        InventoryStockDetails detailsTwo = toStockList().get(1);
        List<InventoryStockDetails> fetchList = inventoryStockRepository.
                getAllInventoryStockByCategory(true,detailsOne.getProductCategory().getCategoryId());
        assertEquals(fetchList.get(0).getStockId(),detailsOne.getStockId());
        assertEquals(fetchList.get(0).getSerialNumber(),detailsOne.getSerialNumber());
        assertEquals(fetchList.get(0).getStatus(),detailsOne.getStatus());
        assertEquals(fetchList.get(0).getBatchDetails().getBatchName(),detailsOne.getBatchDetails().getBatchName());
        assertEquals(fetchList.get(0).getProductDetails().getProductName(),detailsOne.getProductDetails().getProductName());
        assertEquals(fetchList.get(0).getProductCategory().getCategoryName(),detailsOne.getProductCategory().getCategoryName());
        assertEquals(fetchList.get(1).getStockId(),detailsTwo.getStockId());
        assertEquals(fetchList.get(1).getSerialNumber(),detailsTwo.getSerialNumber());
        assertEquals(fetchList.get(1).getStatus(),detailsTwo.getStatus());
        assertEquals(fetchList.get(1).getBatchDetails().getBatchName(),detailsTwo.getBatchDetails().getBatchName());
        assertEquals(fetchList.get(1).getProductDetails().getProductName(),detailsTwo.getProductDetails().getProductName());
        assertEquals(fetchList.get(1).getProductCategory().getCategoryName(),detailsTwo.getProductCategory().getCategoryName());
    }
}
