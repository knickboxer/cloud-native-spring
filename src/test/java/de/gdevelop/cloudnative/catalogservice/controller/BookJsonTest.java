package de.gdevelop.cloudnative.catalogservice.controller;

import de.gdevelop.cloudnative.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class BookJsonTest {

    private static final String TITLE = "Title";
    private static final String AUTHOR = "Author";
    private static final Double PRICE = 1.23;

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = getBook("1234567890");
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }

    @Test
    void testDeserializ() throws Exception {
        var content = """ 
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 3.21
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book ("1234567890", TITLE, AUTHOR, 3.21));
    }

    private Book getBook(String isbn) {
        return new Book(isbn, TITLE, AUTHOR, PRICE);
    }

}
