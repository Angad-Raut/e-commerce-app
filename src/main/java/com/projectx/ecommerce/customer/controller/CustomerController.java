package com.projectx.ecommerce.customer.controller;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import com.projectx.ecommerce.customer.payloads.CustomerDto;
import com.projectx.ecommerce.customer.payloads.ViewCustomerDto;
import com.projectx.ecommerce.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customer/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/insertUpdate")
    public ResponseEntity<ResponseDto<String>> insertUpdate(@RequestBody CustomerDto dto) {
        try {
            String data = customerService.insertUpdate(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.CREATED);
        } catch (ResourceNotFoundException | AlreadyExistException | InvalidDataException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/getCustomerDetailsById")
    public ResponseEntity<ResponseDto<CustomerDto>> getCustomerDetailsById(@RequestBody EntityIdDto dto) {
        try {
            CustomerDto data = customerService.getById(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/updateCustomerStatus")
    public ResponseEntity<ResponseDto<String>> updateCustomerStatus(@RequestBody EntityIdDto dto) {
        try {
            String data = customerService.updateCustomerStatus(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getCustomerDropDown")
    public ResponseEntity<ResponseDto<List<EntityIdAndValueDto>>> getCustomerDropDown() {
        try {
            List<EntityIdAndValueDto> fetchList = customerService.getCustomerDropDown();
            return new ResponseEntity<>(new ResponseDto<>(fetchList,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllCustomers")
    public ResponseEntity<ResponseDto<List<ViewCustomerDto>>> getAllCustomers() {
        try {
            List<ViewCustomerDto> fetchList = customerService.getAllCustomers();
            return new ResponseEntity<>(new ResponseDto<>(fetchList,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getAllEnabledCustomers")
    public ResponseEntity<ResponseDto<List<ViewCustomerDto>>> getAllEnabledCustomers() {
        try {
            List<ViewCustomerDto> fetchList = customerService.getAllEnabledCustomers();
            return new ResponseEntity<>(new ResponseDto<>(fetchList,null),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()),HttpStatus.OK);
        }
    }
}
