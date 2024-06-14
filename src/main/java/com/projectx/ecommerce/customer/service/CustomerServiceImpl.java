package com.projectx.ecommerce.customer.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.customer.payloads.CustomerDto;
import com.projectx.ecommerce.customer.payloads.ViewCustomerDto;
import com.projectx.ecommerce.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public String insertUpdate(CustomerDto dto) throws ResourceNotFoundException, AlreadyExistException, InvalidDataException {
        CustomerDetails customerDetails = null;
        String result = null;
        if (dto.getCustomerId()==null) {
               isMobileExist(dto.getCustomerMobile());
               if (dto.getCustomerEmail()!=null) {
                   isEmailExist(dto.getCustomerEmail());
               }
               customerDetails = CustomerDetails.builder()
                       .customerName(dto.getCustomerName())
                       .customerMobile(dto.getCustomerMobile())
                       .customerEmail(dto.getCustomerEmail())
                       .customerAddress(dto.getCustomerAddress())
                       .customerStatus(true)
                       .isAdmin(isAdmin())
                       .insertedTime(new Date())
                       .updatedTime(new Date())
                       .build();
               result = MessageUtils.ADD_CUSTOMER;
        } else {
               customerDetails = findCustomerById(dto.getCustomerId());
               if (customerDetails==null) {
                   throw new ResourceNotFoundException(MessageUtils.CUSTOMER_NOT_EXIST);
               }
               if (!dto.getCustomerName().equals(customerDetails.getCustomerName())) {
                   customerDetails.setCustomerName(dto.getCustomerName());
               }
               if (!dto.getCustomerMobile().equals(customerDetails.getCustomerMobile())) {
                   customerDetails.setCustomerMobile(dto.getCustomerMobile());
               }
               if (!dto.getCustomerAddress().equals(customerDetails.getCustomerAddress())) {
                   customerDetails.setCustomerAddress(dto.getCustomerAddress());
               }
               if (dto.getCustomerEmail()!=null && customerDetails.getCustomerMobile()==null) {
                   customerDetails.setCustomerEmail(dto.getCustomerEmail());
               } else if (dto.getCustomerEmail()==null && customerDetails.getCustomerEmail()!=null) {
                   customerDetails.setCustomerEmail(dto.getCustomerEmail());
               }
               customerDetails.setUpdatedTime(new Date());
               result = MessageUtils.UPDATE_CUSTOMER;
        }
        try {
             customerRepository.save(customerDetails);
             return result;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (AlreadyExistException e) {
            throw new AlreadyExistException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public CustomerDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        CustomerDetails details = customerRepository.findByCustomerId(dto.getEntityId());
        if (details==null) {
            throw new ResourceNotFoundException(MessageUtils.CUSTOMER_NOT_EXIST);
        }
        return CustomerDto.builder()
                .customerId(details.getCustomerId())
                .customerName(details.getCustomerName())
                .customerMobile(details.getCustomerMobile())
                .customerEmail(details.getCustomerEmail()!=null?details.getCustomerEmail():null)
                .customerAddress(details.getCustomerAddress())
                .build();
    }

    @Override
    public List<EntityIdAndValueDto> getCustomerDropDown() {
        List<Object[]> fecthList = customerRepository.getCustomerDropDown(true);
        return !fecthList.isEmpty()?fecthList.stream()
                .map(data -> {
                    return EntityIdAndValueDto.builder()
                            .entityId(data[0]!=null?Long.parseLong(data[0].toString()):null)
                            .entityValue(data[1]!=null?data[1].toString():null)
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public List<ViewCustomerDto> getAllCustomers() {
        List<CustomerDetails> fetchList = customerRepository.findAll();
        AtomicInteger index=new AtomicInteger(0);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return ViewCustomerDto.builder()
                            .srNo(index.incrementAndGet())
                            .customerId(data.getCustomerId())
                            .customerName(data.getCustomerName())
                            .customerMobile(data.getCustomerMobile())
                            .customerEmail(data.getCustomerEmail()!=null?data.getCustomerEmail():"-")
                            .customerStatus(data.getCustomerStatus()?"Enabled":"Disabled")
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public List<ViewCustomerDto> getAllEnabledCustomers() {
        List<CustomerDetails> fetchList = customerRepository.getAllEnabledCustomers(true);
        AtomicInteger index=new AtomicInteger(0);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return ViewCustomerDto.builder()
                            .srNo(index.incrementAndGet())
                            .customerId(data.getCustomerId())
                            .customerName(data.getCustomerName())
                            .customerMobile(data.getCustomerMobile())
                            .customerEmail(data.getCustomerEmail()!=null?data.getCustomerEmail():"-")
                            .customerStatus(data.getCustomerStatus()?"Enabled":"Disabled")
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public String updateCustomerStatus(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            CustomerDetails details = customerRepository.findByCustomerId(dto.getEntityId());
            if (details==null) {
                throw new ResourceNotFoundException(MessageUtils.CUSTOMER_NOT_EXIST);
            } else {
                Boolean status = setStatus(details.getCustomerStatus());
                String message = status?MessageUtils.CUSTOMER_DISABLE:MessageUtils.CUSTOMER_ENABLE;
                customerRepository.updateCustomerStatus(details.getCustomerId(),status);
                return message;
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
         if (activeProfile.equals("test")) {
             customerRepository.deleteAll();
         }
    }

    private void isMobileExist(Long mobile) {
        if (customerRepository.existsByCustomerMobile(mobile)) {
            throw new AlreadyExistException(MessageUtils.CUSTOMER_MOBILE_EXIST);
        }
    }
    private void isEmailExist(String email) {
        if (customerRepository.existsByCustomerEmail(email)) {
            throw new AlreadyExistException(MessageUtils.CUSTOMER_EMAIL_EXIST);
        }
    }
    private CustomerDetails findCustomerById(Long customerId) {
        CustomerDetails details = customerRepository.findByCustomerId(customerId);
        if (details==null) {
            throw new ResourceNotFoundException(MessageUtils.CUSTOMER_NOT_EXIST);
        }
        return details;
    }
    private Boolean setStatus(Boolean status) {
        if (status) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean isAdmin() {
        if (customerRepository.getCustomerCount().equals(MessageUtils.DEFAULT_COUNT)) {
            return true;
        } else {
            return false;
        }
    }
}
