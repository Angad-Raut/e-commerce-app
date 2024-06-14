package com.projectx.ecommerce.product.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.EntityTypeDto;
import com.projectx.ecommerce.product.payloads.EditProductDto;
import com.projectx.ecommerce.product.payloads.ProductDto;
import com.projectx.ecommerce.product.payloads.ViewProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    String insertUpdate(ProductDto dto) throws ResourceNotFoundException, AlreadyExistException, InvalidDataException, IOException;
    EditProductDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    Boolean updateProductStatus(EntityIdDto dto)throws ResourceNotFoundException;
    List<ViewProductDto> getAllProducts(EntityTypeDto dto);
    List<ViewProductDto> getVerifiedProductsByCategory(EntityIdDto dto);
    void deleteAll();
}
