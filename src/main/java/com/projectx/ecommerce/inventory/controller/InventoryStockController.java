package com.projectx.ecommerce.inventory.controller;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import com.projectx.ecommerce.inventory.payloads.InventoryRequestDto;
import com.projectx.ecommerce.inventory.payloads.InventoryStockDto;
import com.projectx.ecommerce.inventory.payloads.ViewInventoryStockDto;
import com.projectx.ecommerce.inventory.service.InventoryStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/inventoryStock/api")
public class InventoryStockController {

    @Autowired
    private InventoryStockService inventoryStockService;

    @PostMapping(value = "/insertUpdate")
    public ResponseEntity<ResponseDto<String>> insertUpdate(@RequestBody InventoryStockDto dto) {
        try {
            String data = inventoryStockService.insertUpdate(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.CREATED);
        } catch (ResourceNotFoundException | AlreadyExistException | InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getById")
    public ResponseEntity<ResponseDto<InventoryStockDto>> getById(@RequestBody EntityIdDto dto) {
        try {
            InventoryStockDto data = inventoryStockService.getById(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getInventoryStockDropDown")
    public ResponseEntity<ResponseDto<List<EntityIdAndValueDto>>> getInventoryStockDropDown(@RequestBody InventoryRequestDto dto) {
        try {
            List<EntityIdAndValueDto> data = inventoryStockService.getInventoryStockDropDown(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateStockStatus")
    public ResponseEntity<ResponseDto<Boolean>> updateStockStatus(@RequestBody EntityIdDto dto) {
        try {
            Boolean data = inventoryStockService.updateStockStatus(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getAllInventoryStock")
    public ResponseEntity<ResponseDto<List<ViewInventoryStockDto>>> getAllInventoryStock(@RequestBody InventoryRequestDto dto) {
        try {
            List<ViewInventoryStockDto> data = inventoryStockService.getAllInventoryStock(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }
}
