package com.projectx.ecommerce.product.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.product.payloads.CategoryDto;
import com.projectx.ecommerce.product.payloads.ViewCategoryDto;

import java.util.List;

public interface ProductCategoryService {
    String insertUpdate(CategoryDto dto)throws ResourceNotFoundException, AlreadyExistException, InvalidDataException;
    CategoryDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    String updateCategoryStatus(EntityIdDto dto)throws ResourceNotFoundException;
    List<EntityIdAndValueDto> getCategoryDropDown();
    List<ViewCategoryDto> getAllCategories();
    List<ViewCategoryDto> getAllEnabledCategories();
    void deleteAll();
}
