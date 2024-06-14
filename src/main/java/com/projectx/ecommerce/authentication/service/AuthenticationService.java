package com.projectx.ecommerce.authentication.service;

import com.projectx.ecommerce.authentication.payloads.LoginRequestDto;
import com.projectx.ecommerce.authentication.payloads.LoginResponseDto;
import com.projectx.ecommerce.common.exceptions.UserNoFoundException;

public interface AuthenticationService {
    LoginResponseDto getLogin(LoginRequestDto dto)throws UserNoFoundException;
    Boolean logout();
}
