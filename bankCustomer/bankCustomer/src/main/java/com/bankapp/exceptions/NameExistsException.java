package com.bankapp.exceptions;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NameExistsException extends RuntimeException {
    public NameExistsException(String message) {
        super(message);
    }
}
