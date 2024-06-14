package com.projectx.ecommerce.inventory.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.inventory.entity.BatchDetails;
import com.projectx.ecommerce.inventory.entity.InventoryStockDetails;
import com.projectx.ecommerce.inventory.payloads.BatchIdWithQtyDto;
import com.projectx.ecommerce.inventory.payloads.InventoryRequestDto;
import com.projectx.ecommerce.inventory.payloads.InventoryStockDto;
import com.projectx.ecommerce.inventory.payloads.ViewInventoryStockDto;
import com.projectx.ecommerce.inventory.repository.BatchDetailsRepository;
import com.projectx.ecommerce.inventory.repository.InventoryStockRepository;
import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.entity.ProductDetails;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import com.projectx.ecommerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class InventoryStockServiceImpl implements InventoryStockService {

    @Autowired
    private InventoryStockRepository inventoryStockRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BatchDetailsRepository batchDetailsRepository;
    @Autowired
    private BatchService batchService;

    @Override
    public String insertUpdate(InventoryStockDto dto) throws ResourceNotFoundException, AlreadyExistException, InvalidDataException {
        try {
            isSerialNoExist(dto.getSerialNumber());
            BatchDetails batchDetails = batchDetailsRepository.findByBatchId(dto.getBatchId());
            ProductCategory category = productCategoryRepository.findByCategoryId(dto.getCategoryId());
            ProductDetails productDetails = productRepository.findByProductId(dto.getProductId());
            InventoryStockDetails inventoryStockDetails = InventoryStockDetails.builder()
                    .batchDetails(batchDetails)
                    .productCategory(category)
                    .productDetails(productDetails)
                    .serialNumber(dto.getSerialNumber())
                    .status(true)
                    .insertedTime(new Date())
                    .updatedTime(new Date())
                    .build();
            inventoryStockRepository.save(inventoryStockDetails);
            batchService.updateBatchQuantity(new BatchIdWithQtyDto(dto.getCategoryId(),
                    dto.getProductId(),dto.getBatchId(),1));
            return MessageUtils.ADD_STOCK;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (AlreadyExistException e) {
            throw new AlreadyExistException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public InventoryStockDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            InventoryStockDetails details = inventoryStockRepository.findByStockId(dto.getEntityId());
            if (details==null) {
                throw new ResourceNotFoundException(MessageUtils.STOCK_NOT_EXIST);
            }
            return InventoryStockDto.builder()
                    .stockId(details.getStockId())
                    .batchId(details.getBatchDetails().getBatchId())
                    .categoryId(details.getProductCategory().getCategoryId())
                    .productId(details.getProductDetails().getProductId())
                    .serialNumber(details.getSerialNumber())
                    .build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<EntityIdAndValueDto> getInventoryStockDropDown(InventoryRequestDto dto) {
        List<InventoryStockDetails> fetchList = inventoryStockRepository.getAllInventoryStockByCategoryAndProductAndBatch(
                true,dto.getCategoryId(),dto.getProductId(),dto.getBatchId());
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return EntityIdAndValueDto.builder()
                            .entityId(data.getStockId())
                            .entityValue(data.getSerialNumber())
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public Boolean updateStockStatus(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            InventoryStockDetails details = inventoryStockRepository.findByStockId(dto.getEntityId());
            if (details==null) {
                throw new ResourceNotFoundException(MessageUtils.STOCK_NOT_EXIST);
            }
            Integer count = inventoryStockRepository.updateStatus(dto.getEntityId(),false);
            if (count==1) {
                return true;
            } else {
                return false;
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Boolean updateAllStocksStatus(List<EntityIdDto> dtoList) throws ResourceNotFoundException {
        try {
            List<Long> idList = dtoList.stream()
                    .map(EntityIdDto::getEntityId)
                    .collect(Collectors.toList());
            Integer count = inventoryStockRepository.updateAllStatus(idList,false);
            if (count==1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public List<ViewInventoryStockDto> getAllInventoryStock(InventoryRequestDto dto) {
        List<InventoryStockDetails> fetchList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(0);
        if (dto.getCategoryId()!=null && dto.getProductId()!=null && dto.getBatchId()!=null) {
            fetchList = inventoryStockRepository.getAllInventoryStockByCategoryAndProductAndBatch(
                    true, dto.getCategoryId(), dto.getProductId(), dto.getBatchId());
        } else if (dto.getCategoryId()!=null && dto.getProductId()!=null && dto.getBatchId()==null) {
            fetchList = inventoryStockRepository.getAllInventoryStockByCategoryAndProduct(true,
                    dto.getCategoryId(),dto.getProductId());
        } else if (dto.getCategoryId()!=null && dto.getProductId()==null && dto.getBatchId()==null) {
            fetchList = inventoryStockRepository.getAllInventoryStockByCategory(true,dto.getCategoryId());
        } else {
            fetchList = inventoryStockRepository.getAllInventoryStockByStatus(true);
        }
        return !fetchList.isEmpty() ? fetchList.stream()
                .map(data -> {
                    return ViewInventoryStockDto.builder()
                            .srNo(index.incrementAndGet())
                            .stockId(data.getStockId())
                            .serialNumber(data.getSerialNumber())
                            .categoryName(data.getProductCategory().getCategoryName())
                            .productName(data.getProductDetails().getProductName())
                            .batchName(data.getBatchDetails().getBatchName())
                            .status(data.getStatus() ? "Active" : "Completed")
                            .build();
                }).collect(Collectors.toList()) : new ArrayList<>();
    }
    private void isSerialNoExist(String serialNumber) {
        if (inventoryStockRepository.existsBySerialNumber(serialNumber)) {
            throw new AlreadyExistException(MessageUtils.STOCK_ALREADY_EXIST);
        }
    }

}
