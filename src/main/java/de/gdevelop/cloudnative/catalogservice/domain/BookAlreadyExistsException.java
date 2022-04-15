package de.gdevelop.cloudnative.catalogservice.domain;

/**
 * @author Gerhard
 */
public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn) {
        super("The book with ISBN " + isbn + " already exists.");
    }
}
