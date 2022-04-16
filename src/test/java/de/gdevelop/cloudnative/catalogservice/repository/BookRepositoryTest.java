package de.gdevelop.cloudnative.catalogservice.repository;

import de.gdevelop.cloudnative.catalogservice.config.DataJdbcConfig;
import de.gdevelop.cloudnative.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import(DataJdbcConfig.class)  // Imports the data configuration. Needed to enable auditing.
//Disables the default behavior relying on an embedded test database since you’re using Testcontainers.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findBookByIsbnWhenExisting() {
        var bookIsbn = "1234567890";
        var book = Book.build(bookIsbn, "Title", "Author", 12.90, "PublishPress");
        jdbcAggregateTemplate.insert(book);

        Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().isbn()).isEqualTo(bookIsbn);

    }

    //JdbcAggregateTemplate is used to prepare the data targeted by the test
    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;
}
