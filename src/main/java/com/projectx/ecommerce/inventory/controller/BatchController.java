package com.projectx.ecommerce.inventory.controller;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import com.projectx.ecommerce.inventory.payloads.BatchDto;
import com.projectx.ecommerce.inventory.payloads.CategoryAndProductDto;
import com.projectx.ecommerce.inventory.payloads.InventoryRequestDto;
import com.projectx.ecommerce.inventory.payloads.ViewBatchDto;
import com.projectx.ecommerce.inventory.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/batch/api")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping(value = "/insertUpdate")
    public ResponseEntity<ResponseDto<String>> insertUpdate(@RequestBody BatchDto dto) {
        try {
            String data = batchService.insertUpdate(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.CREATED);
        } catch (ResourceNotFoundException | AlreadyExistException | InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getBatchById")
    public ResponseEntity<ResponseDto<BatchDto>> getBatchById(@RequestBody EntityIdDto dto) {
        try {
            BatchDto data = batchService.getById(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateBatchStatus")
    public ResponseEntity<ResponseDto<Boolean>> updateBatchStatus(@RequestBody EntityIdDto dto) {
        try {
            Boolean data = batchService.updateBatchStatus(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getBatchDropDown")
    public ResponseEntity<ResponseDto<List<EntityIdAndValueDto>>> getBatchDropDown(@RequestBody CategoryAndProductDto dto) {
        try {
            List<EntityIdAndValueDto> data = batchService.getBatchDropDown(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getAllBatchList")
    public ResponseEntity<ResponseDto<List<ViewBatchDto>>> getAllBatchList(@RequestBody CategoryAndProductDto dto) {
        try {
            List<ViewBatchDto> data = batchService.getAllBatchList(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }
}
