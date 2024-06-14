package com.projectx.ecommerce.inventory.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.inventory.payloads.InventoryRequestDto;
import com.projectx.ecommerce.inventory.payloads.InventoryStockDto;
import com.projectx.ecommerce.inventory.payloads.ViewInventoryStockDto;

import java.util.List;

public interface InventoryStockService {
    String insertUpdate(InventoryStockDto dto)throws ResourceNotFoundException, AlreadyExistException, InvalidDataException;
    InventoryStockDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    List<EntityIdAndValueDto> getInventoryStockDropDown(InventoryRequestDto dto);
    Boolean updateStockStatus(EntityIdDto dto)throws ResourceNotFoundException;
    Boolean updateAllStocksStatus(List<EntityIdDto> dtoList)throws ResourceNotFoundException;
    List<ViewInventoryStockDto> getAllInventoryStock(InventoryRequestDto dto);
}
