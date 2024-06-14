package com.projectx.ecommerce.repository;

import com.projectx.ecommerce.common.utils.MessageUtils;
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
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    public void clearData() {
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    public List<ProductDetails> toProductList() {
        ProductCategory category = productCategoryRepository.save(CommonRepoUtils.toCategory());
        productRepository.save(CommonRepoUtils.toProduct(category));
        productRepository.save(CommonRepoUtils.toProductTwo(category));
        return productRepository.findAll();
    }

    @Test
    public void find_by_product_id_test() {
        ProductDetails details = toProductList().get(0);
        ProductDetails productDetails = productRepository.findByProductId(details.getProductId());
        assertEquals(productDetails.getProductId(),details.getProductId());
        assertEquals(productDetails.getProductName(),details.getProductName());
        assertEquals(productDetails.getProductDescription(),details.getProductDescription());
        assertEquals(productDetails.getProductPrice(),details.getProductPrice());
    }

    @Test
    public void exists_by_product_name_test() {
        ProductDetails details = toProductList().get(0);
        Boolean status = productRepository.existsByProductName(details.getProductName());
        assertTrue(status);
    }

    @Test
    public void update_product_status_test() {
        ProductDetails details = toProductList().get(0);
        Integer result = productRepository.updateProductStatus(details.getProductId(),MessageUtils.VERIFY_STATUS);
        assertEquals(result,1);
    }

    @Test
    public void get_all_products_by_status_test() {
        ProductDetails details = toProductList().get(0);;
        ProductDetails details1 = toProductList().get(1);;
        List<ProductDetails> fetchList = productRepository.getAllProductsByStatus(MessageUtils.ADDED_STATUS);
        assertEquals(fetchList.get(0).getProductId(),details.getProductId());
        assertEquals(fetchList.get(0).getProductName(),details.getProductName());
        assertEquals(fetchList.get(0).getProductDescription(),details.getProductDescription());
        assertEquals(fetchList.get(0).getProductPrice(),details.getProductPrice());
        assertEquals(fetchList.get(1).getProductId(),details1.getProductId());
        assertEquals(fetchList.get(1).getProductName(),details1.getProductName());
        assertEquals(fetchList.get(1).getProductDescription(),details1.getProductDescription());
        assertEquals(fetchList.get(1).getProductPrice(),details1.getProductPrice());
    }

    @Test
    public void get_all_verified_products_by_category_test() {
        ProductDetails details = toProductList().get(0);;
        ProductDetails details1 = toProductList().get(1);;
        productRepository.updateProductStatus(details.getProductId(),MessageUtils.VERIFY_STATUS);
        productRepository.updateProductStatus(details1.getProductId(),MessageUtils.VERIFY_STATUS);
        List<ProductDetails> fetchList = productRepository.getAllVerifiedProductsByCategory(
                MessageUtils.VERIFY_STATUS,details1.getProductCategory().getCategoryId());
        assertEquals(fetchList.get(0).getProductId(),details.getProductId());
        assertEquals(fetchList.get(0).getProductName(),details.getProductName());
        assertEquals(fetchList.get(0).getProductDescription(),details.getProductDescription());
        assertEquals(fetchList.get(0).getProductPrice(),details.getProductPrice());
        assertEquals(fetchList.get(1).getProductId(),details1.getProductId());
        assertEquals(fetchList.get(1).getProductName(),details1.getProductName());
        assertEquals(fetchList.get(1).getProductDescription(),details1.getProductDescription());
        assertEquals(fetchList.get(1).getProductPrice(),details1.getProductPrice());
    }

}
