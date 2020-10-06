package org.step.linked.step.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvise extends ResponseEntityExceptionHandler {
    final String mistakeMessage = "Something went wrong";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {

        ExceptionResponseEntity exResponse = new ExceptionResponseEntity();

        exResponse.message = ex.getLocalizedMessage();
        exResponse.path = request.getContextPath();
        exResponse.cause = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        f -> !StringUtils.isEmpty(f.getDefaultMessage())
                                ? f.getDefaultMessage()
                                : mistakeMessage
                ));

        return ResponseEntity
                .status(status)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exResponse);
    }
}
