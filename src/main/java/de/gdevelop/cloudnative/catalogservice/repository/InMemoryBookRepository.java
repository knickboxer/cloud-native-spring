package de.gdevelop.cloudnative.catalogservice.repository;

import de.gdevelop.cloudnative.catalogservice.service.BookRepository;
import de.gdevelop.cloudnative.catalogservice.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gerhard
 */
@Repository
public class InMemoryBookRepository implements BookRepository {
    private static final Map<String, Book> books = new ConcurrentHashMap<>();


    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.containsKey(isbn);
    }

    @Override
    public Book save(Book book) {
        return books.put(book.isbn(), book);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }
}
