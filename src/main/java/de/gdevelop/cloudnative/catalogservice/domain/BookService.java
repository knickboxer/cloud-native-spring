package de.gdevelop.cloudnative.catalogservice.domain;

import de.gdevelop.cloudnative.catalogservice.repository.BookRepository;
import org.springframework.stereotype.Service;

/**
 * @author Gerhard
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new BookNotFoundException(isbn)
        );
    }

    public Book addBookToCatalog(Book book) throws BookAlreadyExistsException {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }

        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book) {
        var existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook.isEmpty()) {
            return addBookToCatalog(book);
        }
        var bookToUpdate = new Book(
                existingBook.get().id(),
                isbn,
                book.title(),
                book.author(),
                book.price(),
                book.publisher(),
                existingBook.get().createdDate(),
                existingBook.get().lastModifiedDate(),
                existingBook.get().version()
        );
        return bookRepository.save(bookToUpdate);
    }
}
