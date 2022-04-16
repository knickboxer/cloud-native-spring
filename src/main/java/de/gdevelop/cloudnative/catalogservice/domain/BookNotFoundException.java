package de.gdevelop.cloudnative.catalogservice.domain;

/**
 * @author Gerhard
 */
public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String isbn) {
        super("The book with ISBN " + isbn + " was not found." );
    }
}
