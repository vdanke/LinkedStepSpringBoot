package org.step.linked.step.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvise extends ResponseEntityExceptionHandler {
    final String mistakeMessage = "Something went wrong";

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
    }

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

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ExceptionResponseEntity> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request
    ) {
        ExceptionResponseEntity exResponse = new ExceptionResponseEntity();

        HttpHeaders httpHeaders = new HttpHeaders();

        exResponse.path = request.getContextPath();
        exResponse.message = ex.getMessage();
        exResponse.cause = Map.of("User ID", ex.getId().toString());

        httpHeaders.add("Description", ex.toString());

        return ResponseEntity
                .status(404)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exResponse);
    }
}
