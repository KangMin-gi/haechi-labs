package com.flowerbun.haechilabs.exception;

import lombok.AllArgsConstructor;


public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
