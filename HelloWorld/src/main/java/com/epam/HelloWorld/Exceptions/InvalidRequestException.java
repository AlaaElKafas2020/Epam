package com.epam.HelloWorld.Exceptions;

import org.springframework.validation.BindingResult;

public class InvalidRequestException extends RuntimeException {

    private BindingResult bindingResult;

    public InvalidRequestException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
