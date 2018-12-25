package com.unacast.rest.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {ExecutionException.class, InterruptedException.class})
    public ResponseStatusException handleListenableFutureExceptions(Exception ex) {
        return new ResponseStatusException(INTERNAL_SERVER_ERROR, "error while processing job", ex);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseStatusException handleUnknownException(Exception ex) {
        return new ResponseStatusException(INTERNAL_SERVER_ERROR, "unknown error", ex);
    }

}
