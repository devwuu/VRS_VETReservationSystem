package com.web.vt.exceptions;

import com.web.vt.common.ExceptionResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponseVO> notFoundExceptionHandler(NotFoundException e){
        log.error("not found exception", e);
        e.printStackTrace();
        ExceptionResponseVO body = new ExceptionResponseVO().status(HttpStatus.BAD_REQUEST).message(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponseVO> validationExceptionHandler(ValidationException e){
        log.error("validation exception", e);
        e.printStackTrace();
        ExceptionResponseVO body = new ExceptionResponseVO().status(HttpStatus.BAD_REQUEST).message(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionResponseVO> invalidTokenExceptionHandler(ValidationException e){
        log.error("invalid token exception", e);
        e.printStackTrace();
        ExceptionResponseVO body = new ExceptionResponseVO().status(HttpStatus.UNAUTHORIZED).message(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResponseVO> commonExceptionHandler(ValidationException e){
        log.error("common exception", e);
        e.printStackTrace();
        ExceptionResponseVO body = new ExceptionResponseVO().status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
