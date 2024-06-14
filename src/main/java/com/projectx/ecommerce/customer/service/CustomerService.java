package com.projectx.ecommerce.customer.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.customer.payloads.CustomerDto;
import com.projectx.ecommerce.customer.payloads.ViewCustomerDto;

import java.util.List;

public interface CustomerService {
    String insertUpdate(CustomerDto dto)throws ResourceNotFoundException,
            AlreadyExistException, InvalidDataException;
    CustomerDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    List<EntityIdAndValueDto> getCustomerDropDown();
    List<ViewCustomerDto> getAllCustomers();
    List<ViewCustomerDto> getAllEnabledCustomers();
    String updateCustomerStatus(EntityIdDto dto)throws ResourceNotFoundException;
    void deleteAll();
}
