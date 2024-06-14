package com.projectx.ecommerce.service;

import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.EntityTypeDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.product.payloads.EditProductDto;
import com.projectx.ecommerce.product.payloads.ViewProductDto;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import com.projectx.ecommerce.product.repository.ProductRepository;
import com.projectx.ecommerce.product.service.ProductServiceImpl;
import com.projectx.ecommerce.utils.CategoryTestUtils;
import com.projectx.ecommerce.utils.ProductTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest extends ProductTestUtils {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void add_product_test() throws IOException {
        when(productRepository.existsByProductName("TubeLight")).thenReturn(false);
        when(productCategoryRepository.findByCategoryId(1L)).thenReturn(CategoryTestUtils.toCategoryEntity());
        when(productRepository.save(toProductEntity())).thenReturn(toProductEntity());
        String message = productService.insertUpdate(toProductDto());
        assertThat(message).isSameAs(MessageUtils.ADD_PRODUCT);
    }

    @Test
    public void get_product_by_id_test() {
        when(productRepository.findByProductId(1L)).thenReturn(toProductEntity());
        EditProductDto editProductDto = productService.getById(new EntityIdDto(1L));
        assertEquals(editProductDto.getProductId(),toProductEntity().getProductId());
        assertEquals(editProductDto.getProductCategoryId(),toProductEntity().getProductCategory().getCategoryId());
        assertEquals(editProductDto.getProductName(),toProductEntity().getProductName());
        assertEquals(editProductDto.getProductPrice(),toProductEntity().getProductPrice());
        assertEquals(editProductDto.getProductDescription(),toProductEntity().getProductDescription());
    }

    @Test
    public void update_product_status_test() {
        when(productRepository.findByProductId(1L)).thenReturn(toProductEntity());
        when(productRepository.updateProductStatus(1L,MessageUtils.VERIFY_STATUS)).thenReturn(1);
        Boolean status = productService.updateProductStatus(new EntityIdDto(1L));
        assertTrue(status);
    }

    @Test
    public void get_all_products_test() {
        when(productRepository.getAllProductsByStatus(MessageUtils.ADDED_STATUS)).thenReturn(toFetchList(true));
        List<ViewProductDto> fetchList = productService.getAllProducts(new EntityTypeDto(MessageUtils.ADDED_STATUS));
        assertEquals(fetchList.get(0).getSrNo(),1);
        assertEquals(fetchList.get(0).getProductId(),toFetchList(true).get(0).getProductId());
        assertEquals(fetchList.get(0).getProductName(),toFetchList(true).get(0).getProductName());
        assertEquals(fetchList.get(0).getProductPrice(),toFetchList(true).get(0).getProductPrice());
        assertEquals(fetchList.get(0).getProductCategory(),toFetchList(true).get(0).getProductCategory().getCategoryName());
        assertEquals(fetchList.get(0).getProductStatus(),toFetchList(true).get(0).getProductStatus().equals(MessageUtils.ADDED_STATUS)?"Inserted":"Verified");
        assertEquals(fetchList.get(1).getSrNo(),2);
        assertEquals(fetchList.get(1).getProductId(),toFetchList(true).get(1).getProductId());
        assertEquals(fetchList.get(1).getProductName(),toFetchList(true).get(1).getProductName());
        assertEquals(fetchList.get(1).getProductPrice(),toFetchList(true).get(1).getProductPrice());
        assertEquals(fetchList.get(1).getProductCategory(),toFetchList(true).get(1).getProductCategory().getCategoryName());
        assertEquals(fetchList.get(1).getProductStatus(),toFetchList(true).get(1).getProductStatus().equals(MessageUtils.ADDED_STATUS)?"Inserted":"Verified");
    }

    @Test
    public void get_verified_products_by_category_test() {
        when(productRepository.getAllVerifiedProductsByCategory(MessageUtils.VERIFY_STATUS,1L)).thenReturn(toFetchList(false));
        List<ViewProductDto> fetchList = productService.getVerifiedProductsByCategory(new EntityIdDto(1L));
        assertEquals(fetchList.get(0).getSrNo(),1);
        assertEquals(fetchList.get(0).getProductId(),toFetchList(false).get(0).getProductId());
        assertEquals(fetchList.get(0).getProductName(),toFetchList(false).get(0).getProductName());
        assertEquals(fetchList.get(0).getProductPrice(),toFetchList(false).get(0).getProductPrice());
        assertEquals(fetchList.get(0).getProductCategory(),toFetchList(false).get(0).getProductCategory().getCategoryName());
        assertEquals(fetchList.get(0).getProductStatus(),toFetchList(false).get(0).getProductStatus().equals(MessageUtils.ADDED_STATUS)?"Inserted":"Verified");
        assertEquals(fetchList.get(1).getSrNo(),2);
        assertEquals(fetchList.get(1).getProductId(),toFetchList(false).get(1).getProductId());
        assertEquals(fetchList.get(1).getProductName(),toFetchList(false).get(1).getProductName());
        assertEquals(fetchList.get(1).getProductPrice(),toFetchList(false).get(1).getProductPrice());
        assertEquals(fetchList.get(1).getProductCategory(),toFetchList(false).get(1).getProductCategory().getCategoryName());
        assertEquals(fetchList.get(1).getProductStatus(),toFetchList(false).get(1).getProductStatus().equals(MessageUtils.ADDED_STATUS)?"Inserted":"Verified");
    }
}
