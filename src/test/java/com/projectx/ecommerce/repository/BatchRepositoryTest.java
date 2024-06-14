package com.projectx.ecommerce.repository;

import com.projectx.ecommerce.inventory.entity.BatchDetails;
import com.projectx.ecommerce.inventory.repository.BatchDetailsRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class BatchRepositoryTest {

    @Autowired
    private BatchDetailsRepository batchDetailsRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void clearData() {
        batchDetailsRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    public List<BatchDetails> toBatchList() {
        ProductCategory category = productCategoryRepository.save(CommonRepoUtils.toCategory());
        ProductDetails productDetails = productRepository.save(CommonRepoUtils.toProduct(category));
        ProductDetails productDetailsTwo = productRepository.save(CommonRepoUtils.toProductTwo(category));
        batchDetailsRepository.save(CommonRepoUtils.toBatch(category,productDetails));
        batchDetailsRepository.save(CommonRepoUtils.toBatchTwo(category,productDetailsTwo));
        return batchDetailsRepository.findAll();
    }

    @Test
    public void findByBatchIdTest() {
        BatchDetails batchDetails = toBatchList().get(0);
        BatchDetails details = batchDetailsRepository.findByBatchId(batchDetails.getBatchId());
        assertEquals(details.getBatchId(),batchDetails.getBatchId());
        assertEquals(details.getBatchName(),batchDetails.getBatchName());
        assertEquals(details.getBatchQty(),batchDetails.getBatchQty());
        assertEquals(details.getRemainingQty(),batchDetails.getRemainingQty());
    }

    @Test
    public void existsByBatchNameTest() {
        BatchDetails batchDetails = toBatchList().get(0);
        Boolean status = batchDetailsRepository.existsByBatchName(batchDetails.getBatchName());
        assertTrue(status);
    }

    @Test
    public void updateBatchStatusTest() {
        BatchDetails batchDetails = toBatchList().get(0);
        Integer result = batchDetailsRepository.updateBatchStatus(batchDetails.getBatchId(),false);
        assertEquals(result,1);
    }

    @Test
    public void updateBatchQuantityTest() {
        BatchDetails batchDetails = toBatchList().get(0);
        Integer result = batchDetailsRepository.updateBatchQuantity(batchDetails.getBatchId(),1,
                batchDetails.getProductCategory().getCategoryId(),
                batchDetails.getProductDetails().getProductId(),true);
        assertEquals(result,1);
    }

    @Test
    public void getRemainingQtyTest() {
        BatchDetails batchDetails = toBatchList().get(0);
        Integer remainingQty = (batchDetails.getRemainingQty()-1);
        batchDetailsRepository.updateBatchQuantity(batchDetails.getBatchId(),remainingQty,
                batchDetails.getProductCategory().getCategoryId(),
                batchDetails.getProductDetails().getProductId(),true);
        Integer quantity = batchDetailsRepository.getRemainingQty(batchDetails.getProductCategory().getCategoryId(),
                batchDetails.getProductDetails().getProductId(),batchDetails.getBatchId(),true);
        assertEquals(quantity,9);
    }

    @Test
    public void getAllBatchByStatusTest() {
        BatchDetails batchDetailsOne = toBatchList().get(0);
        BatchDetails batchDetailsTwo = toBatchList().get(1);
        List<BatchDetails> fetchList = batchDetailsRepository.getAllBatchByStatus(true);
        assertEquals(fetchList.get(0).getBatchId(),batchDetailsOne.getBatchId());
        assertEquals(fetchList.get(0).getBatchName(),batchDetailsOne.getBatchName());
        assertEquals(fetchList.get(0).getBatchQty(),batchDetailsOne.getBatchQty());
        assertEquals(fetchList.get(0).getRemainingQty(),batchDetailsOne.getRemainingQty());
        assertEquals(fetchList.get(1).getBatchId(),batchDetailsTwo.getBatchId());
        assertEquals(fetchList.get(1).getBatchName(),batchDetailsTwo.getBatchName());
        assertEquals(fetchList.get(1).getBatchQty(),batchDetailsTwo.getBatchQty());
        assertEquals(fetchList.get(1).getRemainingQty(),batchDetailsTwo.getRemainingQty());
    }

    @Test
    public void getAllBatchListWithCategoryTest() {
        BatchDetails batchDetailsOne = toBatchList().get(0);
        BatchDetails batchDetailsTwo = toBatchList().get(1);
        List<BatchDetails> fetchList = batchDetailsRepository.getAllBatchListWithCategory(
                batchDetailsOne.getProductCategory().getCategoryId(),true);
        assertEquals(fetchList.get(0).getBatchId(),batchDetailsOne.getBatchId());
        assertEquals(fetchList.get(0).getBatchName(),batchDetailsOne.getBatchName());
        assertEquals(fetchList.get(0).getBatchQty(),batchDetailsOne.getBatchQty());
        assertEquals(fetchList.get(0).getRemainingQty(),batchDetailsOne.getRemainingQty());
        assertEquals(fetchList.get(1).getBatchId(),batchDetailsTwo.getBatchId());
        assertEquals(fetchList.get(1).getBatchName(),batchDetailsTwo.getBatchName());
        assertEquals(fetchList.get(1).getBatchQty(),batchDetailsTwo.getBatchQty());
        assertEquals(fetchList.get(1).getRemainingQty(),batchDetailsTwo.getRemainingQty());
    }

    @Test
    public void getAllBatchListWithCategoryAndProductTest() {
        BatchDetails batchDetailsOne = toBatchList().get(0);
        List<BatchDetails> fetchList = batchDetailsRepository.getAllBatchListWithCategoryAndProduct(
                batchDetailsOne.getProductCategory().getCategoryId(),
                batchDetailsOne.getProductDetails().getProductId(),true);
        assertEquals(fetchList.get(0).getBatchId(),batchDetailsOne.getBatchId());
        assertEquals(fetchList.get(0).getBatchName(),batchDetailsOne.getBatchName());
        assertEquals(fetchList.get(0).getBatchQty(),batchDetailsOne.getBatchQty());
        assertEquals(fetchList.get(0).getRemainingQty(),batchDetailsOne.getRemainingQty());
    }

    @Test
    public void getBatchDropDownTest() {
        BatchDetails batchDetails = toBatchList().get(0);
        List<Object[]> fetchList = batchDetailsRepository.getBatchDropDown(
                batchDetails.getProductCategory().getCategoryId(),
                batchDetails.getProductDetails().getProductId(),true);
        Object[] dropDown = fetchList.get(0);
        assertEquals(Long.parseLong(dropDown[0].toString()),batchDetails.getBatchId());
        assertEquals(dropDown[1].toString(),batchDetails.getBatchName());
    }
}
