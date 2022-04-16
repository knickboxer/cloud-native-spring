package de.gdevelop.cloudnative.catalogservice.controller;

import de.gdevelop.cloudnative.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class BookJsonTest {

    private static final String TITLE = "Title";
    private static final String AUTHOR = "Author";
    private static final Double PRICE = 1.23;
    public static final String DATETIME_STRING = "2021-09-07T22:50:37.135029Z";
    private static final String PUBLISHER = "Publisher";

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = getBook("1234567890");
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(book.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathStringValue("@.publisher").isEqualTo(book.publisher());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version").isEqualTo(book.version());
    }

    @Test
    void testDeserializ() throws Exception {
        var content = """ 
                {
                    "id": 1,
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 1.23,
                    "publisher": "Publisher",
                    "createdDate": "2021-09-07T22:50:37.135029Z",
                    "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
                    "version": 1              
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(getBook("1234567890"));
    }

    private Book getBook(String isbn) {
        var instant = Instant.parse(DATETIME_STRING);
        return new Book(1L, isbn, TITLE, AUTHOR, PRICE, PUBLISHER, instant, instant, 1);
    }

}
