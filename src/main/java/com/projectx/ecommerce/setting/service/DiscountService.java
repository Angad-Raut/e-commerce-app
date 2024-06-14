package com.projectx.ecommerce.setting.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.EntityTypeAndValueDto;
import com.projectx.ecommerce.setting.payloads.DiscountDto;
import com.projectx.ecommerce.setting.payloads.ViewDiscountDto;

import java.util.List;

public interface DiscountService {
    Boolean insertOrUpdate(DiscountDto dto)throws InvalidDataException,AlreadyExistException, ResourceNotFoundException;
    DiscountDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    Boolean updateStatus(EntityIdDto dto)throws ResourceNotFoundException;
    List<EntityTypeAndValueDto> getDropDown();
    List<ViewDiscountDto> getAllDiscountList();
}
