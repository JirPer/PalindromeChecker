package com.medicaments.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicamentNotFoundException extends RuntimeException{
    public MedicamentNotFoundException(String message) {
        super(message);
    }
}
