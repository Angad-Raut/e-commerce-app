package com.projectx.ecommerce.inventory.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.inventory.entity.BatchDetails;
import com.projectx.ecommerce.inventory.payloads.*;
import com.projectx.ecommerce.inventory.repository.BatchDetailsRepository;
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
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchDetailsRepository batchDetailsRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String insertUpdate(BatchDto dto) throws ResourceNotFoundException, AlreadyExistException, InvalidDataException {
        BatchDetails batchDetails = null;
        String result = null;
        if (dto.getBatchId()==null) {
               isBatchExist(dto.getBatchName());
               ProductCategory productCategory = productCategoryRepository.findByCategoryId(dto.getCategoryId());
               ProductDetails productDetails = productRepository.findByProductId(dto.getProductId());
               batchDetails = BatchDetails.builder()
                       .batchName(dto.getBatchName())
                       .batchQty(dto.getBatchQty())
                       .batchStatus(true)
                       .remainingQty(dto.getBatchQty())
                       .productCategory(productCategory)
                       .productDetails(productDetails)
                       .insertedTime(new Date())
                       .updatedTime(new Date())
                       .build();
               result = MessageUtils.ADD_BATCH;
        } else {
               batchDetails = batchDetailsRepository.findByBatchId(dto.getBatchId());
               if (batchDetails==null) {
                   throw new ResourceNotFoundException(MessageUtils.BATCH_NOT_EXIST);
               }
               if (!dto.getBatchName().equals(batchDetails.getBatchName())) {
                   isBatchExist(dto.getBatchName());
                   batchDetails.setBatchName(dto.getBatchName());
               }
               if (!dto.getBatchQty().equals(batchDetails.getBatchQty())) {
                   batchDetails.setBatchQty(dto.getBatchQty());
                   batchDetails.setRemainingQty(dto.getBatchQty());
               }
               if (!dto.getCategoryId().equals(batchDetails.getProductCategory().getCategoryId())) {
                   ProductCategory productCategory = productCategoryRepository.findByCategoryId(dto.getCategoryId());
                   batchDetails.setProductCategory(productCategory);
               }
               if (!dto.getProductId().equals(batchDetails.getProductDetails().getProductId())) {
                   ProductDetails productDetails = productRepository.findByProductId(dto.getProductId());
                   batchDetails.setProductDetails(productDetails);
               }
               batchDetails.setUpdatedTime(new Date());
               result = MessageUtils.UPDATE_BATCH;
        }
        try {
            batchDetailsRepository.save(batchDetails);
            return result;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (AlreadyExistException e) {
            throw new AlreadyExistException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public BatchDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            BatchDetails batchDetails = batchDetailsRepository.findByBatchId(dto.getEntityId());
            if (batchDetails==null) {
                throw new ResourceNotFoundException(MessageUtils.BATCH_NOT_EXIST);
            }
            return BatchDto.builder()
                    .batchId(batchDetails.getBatchId())
                    .batchName(batchDetails.getBatchName())
                    .batchQty(batchDetails.getBatchQty())
                    .categoryId(batchDetails.getProductCategory().getCategoryId())
                    .productId(batchDetails.getProductDetails().getProductId())
                    .build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Boolean updateBatchStatus(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            BatchDetails batchDetails = batchDetailsRepository.findByBatchId(dto.getEntityId());
            if (batchDetails==null) {
                throw new ResourceNotFoundException(MessageUtils.BATCH_NOT_EXIST);
            }
            Integer count = batchDetailsRepository.updateBatchStatus(dto.getEntityId(),false);
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
    public List<EntityIdAndValueDto> getBatchDropDown(CategoryAndProductDto dto) {
        List<Object[]> fetchList = batchDetailsRepository.getBatchDropDown(dto.getCategoryId(),dto.getProductId(),true);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return EntityIdAndValueDto.builder()
                            .entityId(data[0]!=null?Long.parseLong(data[0].toString()):null)
                            .entityValue(data[1]!=null?data[1].toString():null)
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public List<ViewBatchDto> getAllBatchList(CategoryAndProductDto dto) {
        List<BatchDetails> fetchList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(0);
        if (dto.getCategoryId()!=null && dto.getProductId()!=null) {
            fetchList = batchDetailsRepository.getAllBatchListWithCategoryAndProduct(dto.getCategoryId(),
                    dto.getProductId(),true);
        } else if (dto.getCategoryId()!=null && dto.getProductId()==null) {
            fetchList = batchDetailsRepository.getAllBatchListWithCategory(dto.getCategoryId(),true);
        } else {
            fetchList = batchDetailsRepository.getAllBatchByStatus(true);
        }
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return ViewBatchDto.builder()
                            .srNo(index.incrementAndGet())
                            .batchId(data.getBatchId())
                            .batchName(data.getBatchName())
                            .batchStatus(data.getBatchStatus()?"Active":"Completed")
                            .batchQty(data.getBatchQty())
                            .categoryName(data.getProductCategory().getCategoryName())
                            .remainingQty(data.getRemainingQty())
                            .productName(data.getProductDetails().getProductName())
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public void updateBatchQuantity(BatchIdWithQtyDto dto) throws ResourceNotFoundException {
        try {
            Integer fetchQty = batchDetailsRepository.getRemainingQty(dto.getCategoryId(),
                    dto.getProductId(),dto.getBatchId(),true);
            if (dto.getQuantity()>fetchQty) {
                throw new InvalidDataException(MessageUtils.BATCH_LIMIT_EXIST);
            }
            Integer quantity = (fetchQty-dto.getQuantity());
            batchDetailsRepository.updateBatchQuantity(dto.getBatchId(),quantity,dto.getCategoryId(),
                    dto.getProductId(),true);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
    private void isBatchExist(String batchName) {
        if (batchDetailsRepository.existsByBatchName(batchName)) {
            throw new AlreadyExistException(MessageUtils.BATCH_ALREADY_EXIST);
        }
    }
}
