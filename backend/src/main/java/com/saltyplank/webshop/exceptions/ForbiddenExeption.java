package com.saltyplank.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenExeption extends RuntimeException {
    public ForbiddenExeption(String message) {
        super(message);
    }
}
