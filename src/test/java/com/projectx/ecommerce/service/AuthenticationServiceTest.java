package com.projectx.ecommerce.service;

import com.projectx.ecommerce.authentication.payloads.LoginResponseDto;
import com.projectx.ecommerce.authentication.service.AuthenticationService;
import com.projectx.ecommerce.authentication.service.AuthenticationServiceImpl;
import com.projectx.ecommerce.customer.repository.CustomerRepository;
import com.projectx.ecommerce.utils.CommonRepoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void getLoginTest() {
        when(customerRepository.findByCustomerMobile(9766945760L)).thenReturn(CommonRepoUtils.toCustomer());
        LoginResponseDto dto = authenticationService.getLogin(CommonRepoUtils.loginRequest());
        assertEquals(dto.getUserName(),CommonRepoUtils.loginResponse().getUserName());
        assertEquals(dto.getUserEmail(),CommonRepoUtils.loginResponse().getUserEmail());
        assertEquals(dto.getUserMobile(),CommonRepoUtils.loginResponse().getUserMobile());
        assertEquals(dto.getIsAdmin(),CommonRepoUtils.loginResponse().getIsAdmin());
    }

    @Test
    public void getLogoutTest() {
        Boolean result = authenticationService.logout();
        assertTrue(result);
    }
}
