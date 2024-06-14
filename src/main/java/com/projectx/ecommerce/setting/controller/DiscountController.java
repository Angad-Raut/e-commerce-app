package com.projectx.ecommerce.setting.controller;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.EntityTypeAndValueDto;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import com.projectx.ecommerce.setting.payloads.DiscountDto;
import com.projectx.ecommerce.setting.payloads.ViewDiscountDto;
import com.projectx.ecommerce.setting.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping(value = "/insertUpdate")
    public ResponseEntity<ResponseDto<Boolean>> insertUpdate(@RequestBody DiscountDto dto) {
        try {
            Boolean data = discountService.insertOrUpdate(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.CREATED);
        } catch (ResourceNotFoundException | AlreadyExistException | InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getById")
    public ResponseEntity<ResponseDto<DiscountDto>> getById(@RequestBody EntityIdDto dto) {
        try {
            DiscountDto data = discountService.getById(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException | AlreadyExistException | InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateStatus")
    public ResponseEntity<ResponseDto<Boolean>> updateStatus(@RequestBody EntityIdDto dto) {
        try {
            Boolean data = discountService.updateStatus(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getDropDown")
    public ResponseEntity<ResponseDto<List<EntityTypeAndValueDto>>> getDropDown() {
        try {
            List<EntityTypeAndValueDto> data = discountService.getDropDown();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllDiscountList")
    public ResponseEntity<ResponseDto<List<ViewDiscountDto>>> getAllDiscountList() {
        try {
            List<ViewDiscountDto> data = discountService.getAllDiscountList();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }
}
