package com.projectx.ecommerce.service;

import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.inventory.payloads.BatchDto;
import com.projectx.ecommerce.inventory.payloads.BatchIdWithQtyDto;
import com.projectx.ecommerce.inventory.payloads.CategoryAndProductDto;
import com.projectx.ecommerce.inventory.payloads.ViewBatchDto;
import com.projectx.ecommerce.inventory.repository.BatchDetailsRepository;
import com.projectx.ecommerce.inventory.service.BatchServiceImpl;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import com.projectx.ecommerce.product.repository.ProductRepository;
import com.projectx.ecommerce.utils.BatchTestUtils;
import com.projectx.ecommerce.utils.CategoryTestUtils;
import com.projectx.ecommerce.utils.ProductTestUtils;
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
public class BatchServiceTest extends BatchTestUtils {

    @Mock
    private BatchDetailsRepository batchDetailsRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private BatchServiceImpl batchService;

    @Test
    public void add_batch_test() {
        when(batchDetailsRepository.existsByBatchName("BH1001")).thenReturn(false);
        when(productCategoryRepository.findByCategoryId(1L)).thenReturn(CategoryTestUtils.toCategoryEntity());
        when(productRepository.findByProductId(1L)).thenReturn(ProductTestUtils.toProductEntity());
        String message = batchService.insertUpdate(toBatchDto());
        assertThat(message).isSameAs(MessageUtils.ADD_BATCH);
    }

    @Test
    public void get_batch_by_id_test() {
        when(batchDetailsRepository.findByBatchId(1L)).thenReturn(toBatchEntity());
        BatchDto batchDto = batchService.getById(new EntityIdDto(1L));
        assertEquals(batchDto.getBatchId(),toBatchEntity().getBatchId());
        assertEquals(batchDto.getBatchName(),toBatchEntity().getBatchName());
        assertEquals(batchDto.getBatchQty(),toBatchEntity().getBatchQty());
        assertEquals(batchDto.getCategoryId(),toBatchEntity().getProductCategory().getCategoryId());
        assertEquals(batchDto.getProductId(),toBatchEntity().getProductDetails().getProductId());
    }

    @Test
    public void update_batch_status_test() {
        when(batchDetailsRepository.findByBatchId(1L)).thenReturn(toBatchEntity());
        when(batchDetailsRepository.updateBatchStatus(1L,false)).thenReturn(1);
        Boolean status = batchService.updateBatchStatus(new EntityIdDto(1L));
        assertThat(status).isSameAs(true);
    }

    @Test
    public void get_batch_drop_down_test() {
        when(batchDetailsRepository.getBatchDropDown(1L,1L,true)).thenReturn(toObjectDropDown());
        List<EntityIdAndValueDto> fetchList = batchService.getBatchDropDown(new CategoryAndProductDto(1L,1L));
        assertEquals(fetchList.get(0).getEntityId(),1L);
        assertEquals(fetchList.get(0).getEntityValue(),"BH1001");
        assertEquals(fetchList.get(1).getEntityId(),2L);
        assertEquals(fetchList.get(1).getEntityValue(),"BH1002");
    }

    @Test
    public void get_all_batch_list_test() {
        when(batchDetailsRepository.getAllBatchListWithCategoryAndProduct(1L,1L,true)).thenReturn(toFetchList());
        List<ViewBatchDto> fetchList = batchService.getAllBatchList(new CategoryAndProductDto(1L,1L));
        assertEquals(fetchList.get(0).getSrNo(),1);
        assertEquals(fetchList.get(0).getBatchId(),toFetchList().get(0).getBatchId());
        assertEquals(fetchList.get(0).getBatchName(),toFetchList().get(0).getBatchName());
        assertEquals(fetchList.get(0).getBatchQty(),toFetchList().get(0).getBatchQty());
        assertEquals(fetchList.get(0).getRemainingQty(),toFetchList().get(0).getRemainingQty());
        assertEquals(fetchList.get(0).getCategoryName(),toFetchList().get(0).getProductCategory().getCategoryName());
        assertEquals(fetchList.get(0).getProductName(),toFetchList().get(0).getProductDetails().getProductName());
        assertEquals(fetchList.get(0).getBatchStatus(),toFetchList().get(0).getBatchStatus()?"Active":"Completed");
        assertEquals(fetchList.get(1).getSrNo(),2);
        assertEquals(fetchList.get(1).getBatchId(),toFetchList().get(1).getBatchId());
        assertEquals(fetchList.get(1).getBatchName(),toFetchList().get(1).getBatchName());
        assertEquals(fetchList.get(1).getBatchQty(),toFetchList().get(1).getBatchQty());
        assertEquals(fetchList.get(1).getRemainingQty(),toFetchList().get(1).getRemainingQty());
        assertEquals(fetchList.get(1).getCategoryName(),toFetchList().get(1).getProductCategory().getCategoryName());
        assertEquals(fetchList.get(1).getProductName(),toFetchList().get(1).getProductDetails().getProductName());
        assertEquals(fetchList.get(1).getBatchStatus(),toFetchList().get(1).getBatchStatus()?"Active":"Completed");
    }

    @Test
    public void update_batch_quantity_test() {
        when(batchDetailsRepository.getRemainingQty(1L,1L,1L,true)).thenReturn(10);
        when(batchDetailsRepository.updateBatchQuantity(1L,1,1L,1L,true)).thenReturn(1);
        batchService.updateBatchQuantity(new BatchIdWithQtyDto(1L,1L,1L,1));
    }
}
