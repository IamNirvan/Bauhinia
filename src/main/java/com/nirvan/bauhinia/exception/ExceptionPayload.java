package com.nirvan.bauhinia.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class ExceptionPayload {
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;
    private Map<String, String> errorMessageMapping;
}
