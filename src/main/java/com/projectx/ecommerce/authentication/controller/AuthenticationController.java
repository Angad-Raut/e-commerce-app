package com.projectx.ecommerce.authentication.controller;

import com.projectx.ecommerce.authentication.payloads.LoginRequestDto;
import com.projectx.ecommerce.authentication.payloads.LoginResponseDto;
import com.projectx.ecommerce.authentication.service.AuthenticationService;
import com.projectx.ecommerce.common.exceptions.UserNoFoundException;
import com.projectx.ecommerce.common.payloads.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto dto) {
        try {
            LoginResponseDto data = authenticationService.getLogin(dto);
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (UserNoFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<ResponseDto<Boolean>> logout() {
        try {
            Boolean data = authenticationService.logout();
            return new ResponseEntity<>(new ResponseDto<>(data,null),HttpStatus.OK);
        } catch (UserNoFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null,e.getMessage()), HttpStatus.OK);
        }
    }

}
