package com.khantech.ts.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khantech.ts.controller.helper.MessageCodeProvider;
import com.khantech.ts.controller.helper.TransactionApiBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler implements TransactionApiBuilder {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @ExceptionHandler
    protected ResponseEntity<?> handleConflict(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Exception handled -> ", ex);
        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage()
                ));


        var error = new CustomErrorCodeHolder(ErrorCodes.BAD_TRANSACTION_REQUEST.getCode(), objectMapper.writeValueAsString(errors));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        build(null, error));
    }

    @ExceptionHandler
    protected ResponseEntity<?> handleConflict(BaseTSException ex, WebRequest request) {
        log.error("Exception handled -> ", ex);
        return ResponseEntity.status(HttpStatus.OK)
                .body(build(null, ex.getMessageCodeProvider()));
    }

    @ExceptionHandler
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
        log.error("Exception handled -> ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(build(null, ErrorCodes.UNEXPECTED_EXCEPTION));
    }

}
