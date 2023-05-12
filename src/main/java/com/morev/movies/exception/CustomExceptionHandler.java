package com.morev.movies.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.morev.movies.dto.response.ErrorDetails;
import com.morev.movies.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class CustomExceptionHandler<T> {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(BindException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        List<ErrorDetails> errorDetails = new ArrayList<>();
        for (FieldError fieldError : errors) {
            ErrorDetails error = ErrorDetails.builder()
                    .field(fieldError.getField())
                    .detail(fieldError.getDefaultMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
            errorDetails.add(error);
        }

        ErrorResponse errorResponse = new ErrorResponse<>();
        errorResponse.setErrors(errorDetails);

        return errorResponse;
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(ResponseStatusException ex) {
        ErrorResponse response = new ErrorResponse();
        response.addErrorMessageToResponse(ex.getReason());
        return response;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleException(AccountNotFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.addErrorMessageToResponse(ex.getMessage(), "email");
        return response;
    }
}