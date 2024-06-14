package com.projectx.ecommerce.service;

import com.projectx.ecommerce.common.payloads.EntityIdAndValueDto;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.customer.payloads.CustomerDto;
import com.projectx.ecommerce.customer.payloads.ViewCustomerDto;
import com.projectx.ecommerce.customer.repository.CustomerRepository;
import com.projectx.ecommerce.customer.service.CustomerServiceImpl;
import com.projectx.ecommerce.utils.CustomerTestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest extends CustomerTestUtils {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void register_customer_test() {
        when(customerRepository.existsByCustomerMobile(9766945760L)).thenReturn(false);
        when(customerRepository.existsByCustomerEmail("angadraut75@gmail.com")).thenReturn(false);
        String customer = customerService.insertUpdate(toCustomerDto());
        assertThat(customer).isSameAs(MessageUtils.ADD_CUSTOMER);
    }

    @Test
    public void get_customer_by_id_test() {
        when(customerRepository.findByCustomerId(1L)).thenReturn(toCustomerEntity());
        CustomerDto customerDto = customerService.getById(new EntityIdDto(1L));
        assertEquals(customerDto.getCustomerName(),toCustomerDto().getCustomerName());
        assertEquals(customerDto.getCustomerEmail(),toCustomerDto().getCustomerEmail());
        assertEquals(customerDto.getCustomerAddress(),toCustomerDto().getCustomerAddress());
        assertEquals(customerDto.getCustomerMobile(),toCustomerDto().getCustomerMobile());
    }

    @Test
    public void get_customer_drop_down_test() {
        when(customerRepository.getCustomerDropDown(true)).thenReturn(objectList());
        List<EntityIdAndValueDto> dropDown = customerService.getCustomerDropDown();
        assertEquals(dropDown.get(0).getEntityId(),dropDown().get(0).getEntityId());
        assertEquals(dropDown.get(1).getEntityValue(),dropDown().get(1).getEntityValue());
    }

    @Test
    public void get_all_customers_test() {
        when(customerRepository.findAll()).thenReturn(customerDetailsList());
        List<ViewCustomerDto> fetchList = customerService.getAllCustomers();
        assertEquals(fetchList.get(0).getSrNo(),getAllCustomersList().get(0).getSrNo());
        assertEquals(fetchList.get(0).getCustomerId(),getAllCustomersList().get(0).getCustomerId());
        assertEquals(fetchList.get(0).getCustomerName(),getAllCustomersList().get(0).getCustomerName());
        assertEquals(fetchList.get(0).getCustomerEmail(),getAllCustomersList().get(0).getCustomerEmail());
        assertEquals(fetchList.get(0).getCustomerMobile(),getAllCustomersList().get(0).getCustomerMobile());
        assertEquals(fetchList.get(0).getCustomerStatus(),getAllCustomersList().get(0).getCustomerStatus());
        assertEquals(fetchList.get(1).getSrNo(),getAllCustomersList().get(1).getSrNo());
        assertEquals(fetchList.get(1).getCustomerId(),getAllCustomersList().get(1).getCustomerId());
        assertEquals(fetchList.get(1).getCustomerName(),getAllCustomersList().get(1).getCustomerName());
        assertEquals(fetchList.get(1).getCustomerEmail(),getAllCustomersList().get(1).getCustomerEmail());
        assertEquals(fetchList.get(1).getCustomerMobile(),getAllCustomersList().get(1).getCustomerMobile());
        assertEquals(fetchList.get(1).getCustomerStatus(),getAllCustomersList().get(1).getCustomerStatus());
    }

    @Test
    public void get_all_enabled_customers_test() {
        when(customerRepository.getAllEnabledCustomers(true)).thenReturn(customerDetailsList());
        List<ViewCustomerDto> fetchList = customerService.getAllEnabledCustomers();
        assertEquals(fetchList.get(0).getSrNo(),getAllCustomersList().get(0).getSrNo());
        assertEquals(fetchList.get(0).getCustomerId(),getAllCustomersList().get(0).getCustomerId());
        assertEquals(fetchList.get(0).getCustomerName(),getAllCustomersList().get(0).getCustomerName());
        assertEquals(fetchList.get(0).getCustomerEmail(),getAllCustomersList().get(0).getCustomerEmail());
        assertEquals(fetchList.get(0).getCustomerMobile(),getAllCustomersList().get(0).getCustomerMobile());
        assertEquals(fetchList.get(0).getCustomerStatus(),getAllCustomersList().get(0).getCustomerStatus());
        assertEquals(fetchList.get(1).getSrNo(),getAllCustomersList().get(1).getSrNo());
        assertEquals(fetchList.get(1).getCustomerId(),getAllCustomersList().get(1).getCustomerId());
        assertEquals(fetchList.get(1).getCustomerName(),getAllCustomersList().get(1).getCustomerName());
        assertEquals(fetchList.get(1).getCustomerEmail(),getAllCustomersList().get(1).getCustomerEmail());
        assertEquals(fetchList.get(1).getCustomerMobile(),getAllCustomersList().get(1).getCustomerMobile());
        assertEquals(fetchList.get(1).getCustomerStatus(),getAllCustomersList().get(1).getCustomerStatus());
    }

    @Test
    public void update_customer_status_test() {
        //when(customerRepository.save(toCustomerEntity())).thenReturn(toCustomerEntity());
        when(customerRepository.findByCustomerId(1L)).thenReturn(toCustomerEntity());
        when(customerRepository.updateCustomerStatus(1L,false)).thenReturn(1);
        String status = customerService.updateCustomerStatus(new EntityIdDto(1L));
        assertEquals(status,MessageUtils.CUSTOMER_ENABLE);
    }
}
