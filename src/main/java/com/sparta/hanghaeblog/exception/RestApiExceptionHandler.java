package com.sparta.hanghaeblog.exception;

import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestApiException> IllegalArgumentExceptionMessage(IllegalArgumentException e) {
        RestApiException restApiException = new RestApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiException> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        RestApiException restApiException = new RestApiException(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestApiException> AccessDeniedException(AccessDeniedException e){
        RestApiException restApiException = new RestApiException(e.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(restApiException, HttpStatus.FORBIDDEN);
    }

}
