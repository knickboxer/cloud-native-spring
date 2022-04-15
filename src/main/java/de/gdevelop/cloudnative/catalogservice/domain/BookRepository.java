package de.gdevelop.cloudnative.catalogservice.domain;

import java.util.Optional;

/**
 * @author Gerhard
 */
public interface BookRepository {
    Iterable<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    Book save(Book book);

    void deleteByIsbn(String isbn);
}
