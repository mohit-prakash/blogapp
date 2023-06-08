package com.mps.blogapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){
        String msg=manve.toString();
        List<String> errors = Arrays.asList(msg).stream().map(str -> {
            int startIndex = 0, endIndex = str.length() - 1;
            for (int i = str.length() - 1; i >= 0; i--) {
                if (str.charAt(i) == ']') {
                    endIndex--;
                } else if (str.charAt(i) == '[') {
                    startIndex = i + 1;
                    break;
                }
            }
            return str.substring(startIndex, endIndex);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(errors.get(0), HttpStatus.BAD_REQUEST);
    }
}
