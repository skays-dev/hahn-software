package com.hahn.software.exception;


import lombok.Data;

@Data
public class HahnException extends Exception {

    private static final long serialVersionUID = 9156274958431250318L;

    protected int code;

    protected String field;

    protected String message;

    protected String reason;

    public HahnException(String message) {
        super(message);
        this.message = message;
    }

    public HahnException(String message, String reason) {
        super(message);
        this.message = message;
        this.reason = reason;
    }

    public HahnException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public HahnException(int code, String field, String message) {
        super(message);
        this.code = code;
        this.field = field;
        this.message = message;
    }

    public HahnException(int code, String field, String message, String reason) {
        super(message);
        this.code = code;
        this.field = field;
        this.message = message;
        this.reason = reason;
    }

    public HahnException(Throwable cause) {
        super(cause);
    }

    public HahnException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public HahnException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public HahnException(int code, String message, String field, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.field = field;
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }


    public HahnException() {
        super();
    }

}
