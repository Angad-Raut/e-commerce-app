package com.projectx.ecommerce.authentication.service;

import com.projectx.ecommerce.authentication.payloads.LoginRequestDto;
import com.projectx.ecommerce.authentication.payloads.LoginResponseDto;
import com.projectx.ecommerce.common.exceptions.UserNoFoundException;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.customer.entity.CustomerDetails;
import com.projectx.ecommerce.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public LoginResponseDto getLogin(LoginRequestDto dto) throws UserNoFoundException {
        CustomerDetails details = customerRepository.findByCustomerMobile(Long.parseLong(dto.getUsername()));
        if (details!=null) {
            return LoginResponseDto.builder()
                    .userName(details.getCustomerName())
                    .userEmail(details.getCustomerEmail()!=null?details.getCustomerEmail():null)
                    .userMobile(details.getCustomerMobile())
                    .isAdmin(details.getIsAdmin())
                    .build();
        } else {
            throw new UserNoFoundException(MessageUtils.USER_NOT_FOUND);
        }
    }

    @Override
    public Boolean logout() {
        return true;
    }
}
