package com.projectx.ecommerce.common.exceptions;

public class UserNoFoundException extends RuntimeException {
    public UserNoFoundException(String msg) {
        super(msg);
    }
}
