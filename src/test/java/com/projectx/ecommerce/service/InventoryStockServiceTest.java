package com.projectx.ecommerce.service;

import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.inventory.payloads.BatchIdWithQtyDto;
import com.projectx.ecommerce.inventory.payloads.InventoryRequestDto;
import com.projectx.ecommerce.inventory.payloads.InventoryStockDto;
import com.projectx.ecommerce.inventory.payloads.ViewInventoryStockDto;
import com.projectx.ecommerce.inventory.repository.BatchDetailsRepository;
import com.projectx.ecommerce.inventory.repository.InventoryStockRepository;
import com.projectx.ecommerce.inventory.service.BatchService;
import com.projectx.ecommerce.inventory.service.InventoryStockServiceImpl;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import com.projectx.ecommerce.product.repository.ProductRepository;
import com.projectx.ecommerce.utils.BatchTestUtils;
import com.projectx.ecommerce.utils.CategoryTestUtils;
import com.projectx.ecommerce.utils.InventoryStockTestUtils;
import com.projectx.ecommerce.utils.ProductTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryStockServiceTest extends InventoryStockTestUtils {

    @Mock
    private InventoryStockRepository inventoryStockRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BatchDetailsRepository batchDetailsRepository;

    @Mock
    private BatchService batchService;

    @InjectMocks
    private InventoryStockServiceImpl inventoryStockService;

    @Test
    public void add_inventory_stock_test() {
        when(inventoryStockRepository.existsBySerialNumber("SR10001")).thenReturn(false);
        when(batchDetailsRepository.findByBatchId(1L)).thenReturn(BatchTestUtils.toBatchEntity());
        when(productCategoryRepository.findByCategoryId(1L)).thenReturn(CategoryTestUtils.toCategoryEntity());
        when(productRepository.findByProductId(1L)).thenReturn(ProductTestUtils.toProductEntity());
        when(inventoryStockRepository.save(toInventoryStockEntity())).thenReturn(toInventoryStockEntity());
        when(batchDetailsRepository.updateBatchQuantity(1L,1,1L,1L,true)).thenReturn(1);
        batchService.updateBatchQuantity(new BatchIdWithQtyDto(1L,1L,1L,1));
        inventoryStockService.insertUpdate(toInventoryStockDto(true));
    }

    @Test
    public void get_inventory_stock_by_id_test() {
        when(inventoryStockRepository.findByStockId(1L)).thenReturn(toInventoryStockEntity());
        InventoryStockDto inventoryStockDto = inventoryStockService.getById(new EntityIdDto(1L));
        assertEquals(inventoryStockDto.getStockId(),toInventoryStockDto(false).getStockId());
        assertEquals(inventoryStockDto.getCategoryId(),toInventoryStockDto(false).getCategoryId());
        assertEquals(inventoryStockDto.getProductId(),toInventoryStockDto(false).getProductId());
        assertEquals(inventoryStockDto.getBatchId(),toInventoryStockDto(false).getBatchId());
        assertEquals(inventoryStockDto.getSerialNumber(),toInventoryStockDto(false).getSerialNumber());

    }

    @Test
    public void get_inventory_stock_drop_down_test() {
        when(inventoryStockRepository.getAllInventoryStockByCategoryAndProductAndBatch(true,1L,1L,1L)).thenReturn(toFetchList());
        List<EntityIdAndValueDto> fetchList = inventoryStockService.getInventoryStockDropDown(new InventoryRequestDto(1L,1L,1L));
        assertEquals(fetchList.get(0).getEntityId(),toFetchList().get(0).getStockId());
        assertEquals(fetchList.get(0).getEntityValue(),toFetchList().get(0).getSerialNumber());
        assertEquals(fetchList.get(1).getEntityId(),toFetchList().get(1).getStockId());
        assertEquals(fetchList.get(1).getEntityValue(),toFetchList().get(1).getSerialNumber());
    }

    @Test
    public void update_inventory_stock_status_test() {
        when(inventoryStockRepository.findByStockId(1L)).thenReturn(toInventoryStockEntity());
        when(inventoryStockRepository.updateStatus(1L,false)).thenReturn(1);
        Boolean status = inventoryStockService.updateStockStatus(new EntityIdDto(1L));
        assertThat(status).isSameAs(true);
    }

    @Test
    public void update_all_stocks_status_test() {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        List<EntityIdDto> entityIdDtoList = new ArrayList<>();
        entityIdDtoList.add(new EntityIdDto(1L));
        when(inventoryStockRepository.updateAllStatus(idList,false)).thenReturn(1);
        Boolean status = inventoryStockService.updateAllStocksStatus(entityIdDtoList);
        assertThat(status).isSameAs(true);
    }

    @Test
    public void get_all_inventory_stock_test() {
        when(inventoryStockRepository.getAllInventoryStockByCategoryAndProductAndBatch(true,1L,1L,1L)).thenReturn(toFetchList());
        List<ViewInventoryStockDto> fetchList = inventoryStockService.getAllInventoryStock(new InventoryRequestDto(1L,1L,1L));
        assertEquals(fetchList.get(0).getSrNo(), 1);
        assertEquals(fetchList.get(0).getStockId(),toFetchList().get(0).getStockId());
        assertEquals(fetchList.get(0).getSerialNumber(),toFetchList().get(0).getSerialNumber());
        assertEquals(fetchList.get(0).getCategoryName(),toFetchList().get(0).getProductCategory().getCategoryName());
        assertEquals(fetchList.get(0).getProductName(),toFetchList().get(0).getProductDetails().getProductName());
        assertEquals(fetchList.get(0).getBatchName(),toFetchList().get(0).getBatchDetails().getBatchName());
        assertEquals(fetchList.get(0).getStatus(),toFetchList().get(0).getStatus()?"Active" : "Completed");
        assertEquals(fetchList.get(1).getSrNo(), 2);
        assertEquals(fetchList.get(1).getStockId(),toFetchList().get(1).getStockId());
        assertEquals(fetchList.get(1).getSerialNumber(),toFetchList().get(1).getSerialNumber());
        assertEquals(fetchList.get(1).getCategoryName(),toFetchList().get(1).getProductCategory().getCategoryName());
        assertEquals(fetchList.get(1).getProductName(),toFetchList().get(1).getProductDetails().getProductName());
        assertEquals(fetchList.get(1).getBatchName(),toFetchList().get(1).getBatchDetails().getBatchName());
        assertEquals(fetchList.get(1).getStatus(),toFetchList().get(1).getStatus()?"Active" : "Completed");
    }
}
