package com.morev.movies.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ErrorResponse<T> {
    private T data;
    private List<Object> errors;

    public void addErrorMessageToResponse(String message) {
        ErrorDetails error = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .detail(message)
                .build();
        setErrors(Arrays.asList(error));
    }
    public void addErrorMessageToResponse(String message, String field) {
        ErrorDetails error = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .detail(message)
                .field(field)
                .build();
        setErrors(Arrays.asList(error));
    }
}
