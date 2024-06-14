package com.projectx.ecommerce.service;

import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.product.payloads.CategoryDto;
import com.projectx.ecommerce.product.payloads.ViewCategoryDto;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import com.projectx.ecommerce.product.service.ProductCategoryServiceImpl;
import com.projectx.ecommerce.utils.CategoryTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductCategoryServiceTest extends CategoryTestUtils {

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void add_category_test() {
        CategoryDto categoryDto = toCategoryDto();
        categoryDto.setCategoryId(null);
        when(productCategoryRepository.save(toCategoryEntity())).thenReturn(toCategoryEntity());
        when(productCategoryRepository.existsByCategoryName("Electronic")).thenReturn(false);
        when(productCategoryRepository.findByCategoryId(1L)).thenReturn(toCategoryEntity());
        String message = productCategoryService.insertUpdate(categoryDto);
        assertThat(message).isSameAs(MessageUtils.ADD_PRODUCT_CATEGORY);
    }

    @Test
    public void get_category_by_id_test() {
        when(productCategoryRepository.findByCategoryId(1L)).thenReturn(toCategoryEntity());
        CategoryDto categoryDto = productCategoryService.getById(new EntityIdDto(1L));
        assertEquals(categoryDto.getCategoryId(),toCategoryDto().getCategoryId());
        assertEquals(categoryDto.getCategoryName(),toCategoryDto().getCategoryName());
    }

    @Test
    public void update_category_status_test() {
        when(productCategoryRepository.findByCategoryId(1L)).thenReturn(toCategoryEntity());
        when(productCategoryRepository.updateCategoryStatus(1L,true)).thenReturn(1);
        String message = productCategoryService.updateCategoryStatus(new EntityIdDto(1L));
        assertEquals(message,MessageUtils.CATEGORY_ENABLE);
    }

    @Test
    public void category_drop_down_test() {
        when(productCategoryRepository.getCategoryDropDown(true)).thenReturn(objectDropDown());
        List<EntityIdAndValueDto> fetchList = productCategoryService.getCategoryDropDown();
        assertEquals(fetchList.get(0).getEntityId(),toDropDown().get(0).getEntityId());
        assertEquals(fetchList.get(0).getEntityValue(),toDropDown().get(0).getEntityValue());
        assertEquals(fetchList.get(1).getEntityId(),toDropDown().get(1).getEntityId());
        assertEquals(fetchList.get(1).getEntityValue(),toDropDown().get(1).getEntityValue());
    }

    @Test
    public void get_all_categories_test() {
        when(productCategoryRepository.findAll()).thenReturn(toProductCategoryList());
        List<ViewCategoryDto> fetchList = productCategoryService.getAllCategories();
        assertEquals(fetchList.get(0).getSrNo(),toCategoryList().get(0).getSrNo());
        assertEquals(fetchList.get(0).getCategoryId(),toCategoryList().get(0).getCategoryId());
        assertEquals(fetchList.get(0).getCategoryName(),toCategoryList().get(0).getCategoryName());
        assertEquals(fetchList.get(0).getCategoryStatus(),toCategoryList().get(0).getCategoryStatus());
        assertEquals(fetchList.get(1).getSrNo(),toCategoryList().get(1).getSrNo());
        assertEquals(fetchList.get(1).getCategoryId(),toCategoryList().get(1).getCategoryId());
        assertEquals(fetchList.get(1).getCategoryName(),toCategoryList().get(1).getCategoryName());
        assertEquals(fetchList.get(1).getCategoryStatus(),toCategoryList().get(1).getCategoryStatus());
    }

    @Test
    public void get_all_enabled_categories_test() {
        when(productCategoryRepository.getAllEnabledCategories(true)).thenReturn(toProductCategoryList());
        List<ViewCategoryDto> fetchList = productCategoryService.getAllEnabledCategories();
        assertEquals(fetchList.get(0).getSrNo(),toCategoryList().get(0).getSrNo());
        assertEquals(fetchList.get(0).getCategoryId(),toCategoryList().get(0).getCategoryId());
        assertEquals(fetchList.get(0).getCategoryName(),toCategoryList().get(0).getCategoryName());
        assertEquals(fetchList.get(0).getCategoryStatus(),toCategoryList().get(0).getCategoryStatus());
        assertEquals(fetchList.get(1).getSrNo(),toCategoryList().get(1).getSrNo());
        assertEquals(fetchList.get(1).getCategoryId(),toCategoryList().get(1).getCategoryId());
        assertEquals(fetchList.get(1).getCategoryName(),toCategoryList().get(1).getCategoryName());
        assertEquals(fetchList.get(1).getCategoryStatus(),toCategoryList().get(1).getCategoryStatus());
    }
}
