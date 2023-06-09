package com.nirvan.bauhinia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

//
// This annotation tells Spring that this class
// is a class that handles exceptions
//
@ControllerAdvice
public class ApiExceptionHandler {

    //
    // This annotation tells spring that this exception handler will
    // only handle exceptions that are of type ApiRequestException
    //
    @ExceptionHandler(value = ApiRequestException.class)
    /**
     * This exception handler only handles exceptions that are
     * of type ApiRequestException (Custom exception).
     * */
    public ResponseEntity<Object> handleException(ApiRequestException exception) {
        //
        // Create the payload
        //
        ExceptionPayload payload = ExceptionPayload.builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        //
        // Return the response entity with the payload and status code
        //
        return ResponseEntity.badRequest().body(payload);
    }
}
