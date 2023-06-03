package com.medicaments.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MedicamentExistsException extends RuntimeException{
    public MedicamentExistsException(String message) {
        super(message);
    }
}
