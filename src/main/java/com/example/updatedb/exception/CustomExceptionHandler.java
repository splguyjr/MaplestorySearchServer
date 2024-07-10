package com.example.updatedb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UnsupportedCharacterException.class)
    public ResponseEntity<String> handleUnsupportedCharacterException(UnsupportedCharacterException ex) {
        return new ResponseEntity<>("지원하지 않는 캐릭터입니다", HttpStatus.BAD_REQUEST);
    }
}