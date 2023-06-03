package com.medicaments.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VehicleExistsException extends RuntimeException {
    public VehicleExistsException(String message) {
        super(message);
    }
}
