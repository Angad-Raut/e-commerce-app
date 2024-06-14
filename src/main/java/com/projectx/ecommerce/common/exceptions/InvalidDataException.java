package com.projectx.ecommerce.common.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String msg) {
        super(msg);
    }
}
