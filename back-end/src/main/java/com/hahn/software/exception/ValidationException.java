package com.hahn.software.exception;

import lombok.Data;

@Data
public class ValidationException extends HahnException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String reason) {
        super(message, reason);
    }

    public ValidationException(int code, String message) {
        super(code, message);
    }

    public ValidationException(int code, String field, String message) {
        super(code, field, message);
    }

    public ValidationException(int code, String field, String message, String reason) {
        super(code, field, message, reason);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ValidationException(int code, String message, String field, Throwable cause) {
        super(code, message, field, cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public ValidationException() {
        super();
    }


}
