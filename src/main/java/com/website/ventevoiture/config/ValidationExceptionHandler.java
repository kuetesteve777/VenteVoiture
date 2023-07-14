package com.website.ventevoiture.config;

import com.website.ventevoiture.dto.ResultDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultDto handleValidationException(MethodArgumentNotValidException ex) {
        return getResultDto(ex);
    }

    public static ResultDto getResultDto(MethodArgumentNotValidException methodArgumentNotValidException) {
        BindingResult result =  methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        return new ResultDto(false, "Validation failed !" , null, HttpStatus.BAD_REQUEST.value(), errors);
    }
}
