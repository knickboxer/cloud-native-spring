package de.gdevelop.cloudnative.catalogservice.service;

/**
 * @author Gerhard
 */
public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String isbn) {
        super("The Book with the ISBN " + isbn + " was not found." );
    }
}
