package com.atlavik.challenge.controller;

import com.atlavik.challenge.dto.ExceptionDto;
import com.atlavik.challenge.exception.CartNotFoundException;
import com.atlavik.challenge.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handle exceptions, and return ExceptionDto to present exceptions with the proper format
 *
 * @author Abbas
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles CartNotFoundException
     *
     * @param ex catch Exception
     * @return An ExceptionDto
     */
    @ExceptionHandler({CartNotFoundException.class, ProductNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleCartNotFound(Exception ex) {
        return new ExceptionDto(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle other unhandled exceptions
     *
     * @param ex catch Exception
     * @return An ExceptionDto
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleOthers(Exception ex) {
        return new ExceptionDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
