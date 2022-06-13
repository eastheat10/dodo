package com.nhnacademy.accountapi.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.nhnacademy.accountapi.exception.EmailNotFoundException;
import com.nhnacademy.accountapi.exception.LoginUserNotFoundException;
import com.nhnacademy.accountapi.exception.UsernameOverlapException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(value = {
        UsernameOverlapException.class,
        LoginUserNotFoundException.class,
        EmailNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleException(RuntimeException ex) {

        Map<String, String> errors = new HashMap<>();

        errors.put("message", ex.getMessage());
        log.error("{}", ex.getMessage());

        return ResponseEntity.status(BAD_REQUEST)
                             .body(errors);
    }

    @ExceptionHandler(value = {
        MethodArgumentNotValidException.class
    })
    public ResponseEntity<Map<String, String>> handleValidationException(
        MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        errors.put("message", "Validation Exception");

        ex.getBindingResult()
          .getAllErrors()
          .forEach(c -> {
              errors.put(((FieldError) c).getField(), c.getDefaultMessage());
              log.error("{}", c.getDefaultMessage());
          });

        return ResponseEntity.status(BAD_REQUEST)
                             .body(errors);
    }


}
