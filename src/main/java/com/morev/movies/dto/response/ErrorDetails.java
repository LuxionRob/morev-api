package com.morev.movies.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorDetails {
    private String field;
    @NotNull(message = "Error detail cannot be null.")
    private String detail;
    @NotNull(message = "Timestamp cannot be null.")
    private LocalDateTime timestamp;
}
