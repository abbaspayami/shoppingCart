package com.atlavik.challenge.exception;

/**
 * To throw if requested cart does not exists in database
 *
 * @author Abbas
 */
public class CartNotFoundException extends RuntimeException{

    /**
     * constructor
     *
     * @param message given message
     */
    public CartNotFoundException(String message) {
        super(message);
    }

}
