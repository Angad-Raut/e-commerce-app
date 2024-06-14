package com.projectx.ecommerce.inventory.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.inventory.payloads.*;

import java.util.List;

public interface BatchService {
    String insertUpdate(BatchDto dto)throws ResourceNotFoundException, AlreadyExistException, InvalidDataException;
    BatchDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    Boolean updateBatchStatus(EntityIdDto dto)throws ResourceNotFoundException;
    List<EntityIdAndValueDto> getBatchDropDown(CategoryAndProductDto dto);
    List<ViewBatchDto> getAllBatchList(CategoryAndProductDto dto);
    void updateBatchQuantity(BatchIdWithQtyDto dto)throws ResourceNotFoundException;
}
