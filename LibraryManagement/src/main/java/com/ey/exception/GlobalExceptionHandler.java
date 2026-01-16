package com.ey.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ey.dto.response.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler(NotFoundException.class)
 public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest req) {
     ApiErrorResponse body = new ApiErrorResponse(404, "Not Found", ex.getMessage(), req.getRequestURI());
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
 }

 @ExceptionHandler(BadRequestException.class)
 public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
     ApiErrorResponse body = new ApiErrorResponse(400, "Bad Request", ex.getMessage(), req.getRequestURI());
     return ResponseEntity.badRequest().body(body);
 }

 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
     String msg = ex.getBindingResult().getFieldErrors().stream()
             .map(e -> e.getField() + ": " + e.getDefaultMessage())
             .findFirst().orElse("Validation error");
     ApiErrorResponse body = new ApiErrorResponse(400, "Validation Failed", msg, req.getRequestURI());
     return ResponseEntity.badRequest().body(body);
 }

 @ExceptionHandler(ConstraintViolationException.class)
 public ResponseEntity<ApiErrorResponse> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
     ApiErrorResponse body = new ApiErrorResponse(400, "Constraint Violation", ex.getMessage(), req.getRequestURI());
     return ResponseEntity.badRequest().body(body);
 }

 @ExceptionHandler(Exception.class)
 public ResponseEntity<ApiErrorResponse> handleOther(Exception ex, HttpServletRequest req) {
     ApiErrorResponse body = new ApiErrorResponse(500, "Internal Server Error", ex.getMessage(), req.getRequestURI());
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
 }
}

