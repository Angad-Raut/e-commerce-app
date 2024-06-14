package com.projectx.ecommerce.common.exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String msg) {
        super(msg);
    }
}
