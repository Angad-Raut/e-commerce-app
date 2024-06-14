package com.projectx.ecommerce.repository;

import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
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
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    public void clearData() {
        productCategoryRepository.deleteAll();
    }

    public List<ProductCategory> toCategoryList() {
        productCategoryRepository.save(CommonRepoUtils.toCategory());
        productCategoryRepository.save(CommonRepoUtils.toCategoryTwo());
        return productCategoryRepository.findAll();
    }

    @Test
    public void find_by_category_dd_test() {
        ProductCategory details = toCategoryList().get(0);
        ProductCategory productCategory = productCategoryRepository.findByCategoryId(details.getCategoryId());
        assertEquals(productCategory.getCategoryId(),details.getCategoryId());
        assertEquals(productCategory.getCategoryName(),details.getCategoryName());
        assertEquals(productCategory.getCategoryStatus(),details.getCategoryStatus());
    }

    @Test
    public void exists_by_category_name_test() {
        ProductCategory details = toCategoryList().get(0);
        Boolean status = productCategoryRepository.existsByCategoryName(details.getCategoryName());
        assertTrue(status);
    }

    @Test
    public void update_category_status_test() {
        ProductCategory details = toCategoryList().get(0);
        Integer result = productCategoryRepository.updateCategoryStatus(details.getCategoryId(),false);
        assertEquals(result,1);
    }

    @Test
    public void get_all_enabled_categories_test() {
        ProductCategory first = toCategoryList().get(0);
        ProductCategory second = toCategoryList().get(1);
        List<ProductCategory> fetchList = productCategoryRepository.getAllEnabledCategories(true);
        assertEquals(fetchList.get(0).getCategoryId(),first.getCategoryId());
        assertEquals(fetchList.get(0).getCategoryName(),first.getCategoryName());
        assertEquals(fetchList.get(0).getCategoryStatus(),first.getCategoryStatus());
        assertEquals(fetchList.get(1).getCategoryId(),second.getCategoryId());
        assertEquals(fetchList.get(1).getCategoryName(),second.getCategoryName());
        assertEquals(fetchList.get(1).getCategoryStatus(),second.getCategoryStatus());
    }

    @Test
    public void get_category_drop_down_test() {
        ProductCategory first = toCategoryList().get(0);
        ProductCategory second = toCategoryList().get(1);
        List<Object[]> fetchList = productCategoryRepository.getCategoryDropDown(true);
        Object[] firstData = fetchList.get(0);
        Object[] secondData = fetchList.get(1);
        assertEquals(Long.parseLong(firstData[0].toString()),first.getCategoryId());
        assertEquals(firstData[1].toString(),first.getCategoryName());
        assertEquals(Long.parseLong(secondData[0].toString()),second.getCategoryId());
        assertEquals(secondData[1].toString(),second.getCategoryName());
    }

}
