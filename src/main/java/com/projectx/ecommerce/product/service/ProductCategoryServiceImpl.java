package com.projectx.ecommerce.product.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.product.entity.ProductCategory;
import com.projectx.ecommerce.product.payloads.CategoryDto;
import com.projectx.ecommerce.product.payloads.ViewCategoryDto;
import com.projectx.ecommerce.product.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public String insertUpdate(CategoryDto dto) throws ResourceNotFoundException, AlreadyExistException, InvalidDataException {
        ProductCategory details = null;
        String result = null;
        if (dto.getCategoryId()==null) {
               isCategoryExist(dto.getCategoryName());
               details = ProductCategory.builder()
                       .categoryName(dto.getCategoryName())
                       .categoryStatus(true)
                       .insertedTime(new Date())
                       .updatedTime(new Date())
                       .build();
               result = MessageUtils.ADD_PRODUCT_CATEGORY;
        } else {
               details = productCategoryRepository.findByCategoryId(dto.getCategoryId());
               if (details==null) {
                   throw new ResourceNotFoundException(MessageUtils.CATEGORY_NOT_EXIST);
               }
               if (!dto.getCategoryName().equals(details.getCategoryName())) {
                   isCategoryExist(dto.getCategoryName());
                   details.setCategoryName(dto.getCategoryName());
               }
               details.setUpdatedTime(new Date());
               result = MessageUtils.UPDATE_PRODUCT_CATEGORY;
        }
        try {
            productCategoryRepository.save(details);
            return result;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (AlreadyExistException e) {
            throw new AlreadyExistException(e.getMessage());
        } catch (InvalidDataException e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public CategoryDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        try {
             ProductCategory productCategory = productCategoryRepository.findByCategoryId(dto.getEntityId());
             if (productCategory==null) {
                 throw new ResourceNotFoundException(MessageUtils.CATEGORY_NOT_EXIST);
             }
             return CategoryDto.builder()
                     .categoryId(productCategory.getCategoryId())
                     .categoryName(productCategory.getCategoryName())
                     .build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public String updateCategoryStatus(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            ProductCategory productCategory = productCategoryRepository.findByCategoryId(dto.getEntityId());
            if (productCategory==null) {
                throw new ResourceNotFoundException(MessageUtils.CATEGORY_NOT_EXIST);
            } else {
                Boolean status = setStatus(productCategory.getCategoryStatus());
                String message = status?MessageUtils.CATEGORY_DISABLE:MessageUtils.CATEGORY_ENABLE;
                productCategoryRepository.updateCategoryStatus(dto.getEntityId(),status);
                return message;
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<EntityIdAndValueDto> getCategoryDropDown() {
        List<Object[]> fetchList = productCategoryRepository.getCategoryDropDown(true);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return EntityIdAndValueDto.builder()
                            .entityId(data[0]!=null?Long.parseLong(data[0].toString()):null)
                            .entityValue(data[1]!=null?data[1].toString():null)
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public List<ViewCategoryDto> getAllCategories() {
        List<ProductCategory> fetchList = productCategoryRepository.findAll();
        AtomicInteger index = new AtomicInteger(0);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return ViewCategoryDto.builder()
                            .srNo(index.incrementAndGet())
                            .categoryId(data.getCategoryId())
                            .categoryName(data.getCategoryName())
                            .categoryStatus(data.getCategoryStatus()?"Enabled":"Disabled")
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public List<ViewCategoryDto> getAllEnabledCategories() {
        List<ProductCategory> fetchList = productCategoryRepository.getAllEnabledCategories(true);
        AtomicInteger index = new AtomicInteger(0);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return ViewCategoryDto.builder()
                            .srNo(index.incrementAndGet())
                            .categoryId(data.getCategoryId())
                            .categoryName(data.getCategoryName())
                            .categoryStatus(data.getCategoryStatus()?"Enabled":"Disabled")
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public void deleteAll() {
        if (activeProfile.equals("test")) {
            productCategoryRepository.deleteAll();
        }
    }

    private void isCategoryExist(String categoryName) {
        if (productCategoryRepository.existsByCategoryName(categoryName)) {
            throw new AlreadyExistException(MessageUtils.CATEGORY_EXIST);
        }
    }
    private Boolean setStatus(Boolean status) {
        if (status) {
            return false;
        } else {
            return true;
        }
    }
}
