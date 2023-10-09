package com.springgoals.exception;

import com.springgoals.controller.LogController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> exception(EntityNotFoundException exception) {
        logger.debug(" exception thrown: EntityNotFoundException ");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> authException(AuthenticationException exception) {
        logger.debug(" exception thrown: AuthenticationException ");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = QueryException.class)
    public ResponseEntity<Object> exception(QueryException queryException) {
        logger.debug(" exception thrown: QueryException ");
        return new ResponseEntity<>(queryException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ValidationsException.class)
    public ResponseEntity<Object> exception(ValidationsException validationException) {
        logger.debug(" exception thrown: ValidationsException ");
        return new ResponseEntity<>(validationException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> exception(RuntimeException runtimeException) {
        logger.debug(" exception thrown: RuntimeException ");
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception exception) {
        logger.debug(" exception thrown: Exception ");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedExp(AccessDeniedException exception) {
        logger.debug(" exception thrown: AccessDeniedException ");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

}
