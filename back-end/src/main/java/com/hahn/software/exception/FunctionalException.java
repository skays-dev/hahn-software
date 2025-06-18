package com.hahn.software.exception;

import lombok.Data;

@Data
public class FunctionalException extends HahnException {
    public FunctionalException(String message) {
        super(message);
    }

    public FunctionalException(String message, String reason) {
        super(message, reason);
    }

    public FunctionalException(int code, String message) {
        super(code, message);
    }

    public FunctionalException(int code, String field, String message) {
        super(code, field, message);
    }

    public FunctionalException(int code, String field, String message, String reason) {
        super(code, field, message, reason);
    }

    public FunctionalException(Throwable cause) {
        super(cause);
    }

    public FunctionalException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionalException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public FunctionalException(int code, String message, String field, Throwable cause) {
        super(code, message, field, cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public FunctionalException() {
        super();
    }


}
