package com.hahn.software.exception.component;

import com.hahn.software.dto.ResponseDto;
import com.hahn.software.exception.FunctionalException;
import com.hahn.software.exception.HahnException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler({Exception.class, HahnException.class, FunctionalException.class})
    public ResponseEntity<ResponseDto> exceptionHandler(Exception ex) {

        // its required for display on Error Log
        ex.printStackTrace();

        if (ex instanceof FunctionalException) {
            ResponseDto error = new ResponseDto();
            error.setErrorCode((((FunctionalException) ex).getCode() != 0) ? ((FunctionalException) ex).getCode() : HttpStatus.BAD_REQUEST.value());
            error.setMessage(ex.getMessage());
            error.setField(((FunctionalException) ex).getField());
            error.setReason(((FunctionalException) ex).getReason());
            return new ResponseEntity<>(error, HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));

        } else {
            ResponseDto error = new ResponseDto();
            error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.setMessage("Please contact the technical support");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

