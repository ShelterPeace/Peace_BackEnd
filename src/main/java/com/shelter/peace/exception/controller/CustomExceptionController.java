package com.shelter.peace.exception.controller;

import com.shelter.peace.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionController {
    ResponseDTO responseDTO = new ResponseDTO<>();
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> runtime(RuntimeException e) {

        log.info("runtime advice: " + e.getMessage());
        responseDTO.setErrorMessage(e.getMessage());
        responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> httpMessageNotReadable(HttpMessageNotReadableException e) {

        log.info("httpMessageNotReadable advice: " + e.getMessage());
        responseDTO.setErrorMessage("HTTP 요청 값이 누락되었습니다.");
        responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> methodArgumentNotValid(MethodArgumentNotValidException e) {
        log.info("MethodArgumentNotValidException advice: " + e.getMessage());
        responseDTO.setErrorMessage(e.getMessage());
        responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDTO> nullPointerException(NullPointerException e) {
        log.info("NullPointerException advice: " + e.getMessage());
        responseDTO.setErrorMessage(e.getMessage());
        responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
}
