package com.example.demo.exceptions;

import com.example.demo.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ErrorResponseDto> interception(NotEnoughStockException exception) {
        ErrorResponseDto dto = new ErrorResponseDto(exception.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponseDto> emptyData(EmptyResultDataAccessException exception) {
        ErrorResponseDto dto = new ErrorResponseDto("Запрашиваемый ресурс не найден в базе данных", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> validData(MethodArgumentNotValidException exception) {
        StringBuilder errorMessage = new StringBuilder("Ошибка валидации: ");
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ")
                    .append(fieldError.getDefaultMessage()).append("; ");
        }
        ErrorResponseDto dto = new ErrorResponseDto(errorMessage.toString().trim(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }
}
