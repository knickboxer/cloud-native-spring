package de.gdevelop.cloudnative.catalogservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gdevelop.cloudnative.catalogservice.domain.Book;
import de.gdevelop.cloudnative.catalogservice.domain.BookAlreadyExistsException;
import de.gdevelop.cloudnative.catalogservice.domain.BookNotFoundException;
import de.gdevelop.cloudnative.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerMvcTest {

    public static final String TITLE = "Title";
    private static final String AUTHOR = "Author";
    private static final Double PRICE = 1.23;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookService bookService;

    @Test
    void whenGetBooksThenShouldReturnListOfOne() throws Exception {
        String isbn = "1231231231";
        given(bookService.viewBookList()).willReturn(getBookList(isbn));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(List.class)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].isbn", is(isbn)))
                .andExpect(jsonPath("$[0].title", is(TITLE)))
                .andExpect(jsonPath("$[0].author", is(AUTHOR)))
                .andExpect(jsonPath("$[0].price", is(PRICE)));
    }


    @Test
    void whenGetBookByIsbnThenShouldReturnNotFound404() throws Exception {
        String isbn = "1231231231";
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);

        mockMvc
                .perform(get("/books/" + isbn))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateBookThenShouldReturnCreated201() throws Exception {
        String isbn = "1231231231";
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getBook(isbn)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void whenCreateBookThenShouldReturnUnprocessableEntity() throws Exception {
        String isbn = "1231231231";
        Book book = getBook(isbn);

        given(bookService.addBookToCatalog(book)).willThrow(BookAlreadyExistsException.class);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }


    private List<Book> getBookList(String isbn) {
        return List.of(getBook(isbn));
    }

    private Book getBook(String isbn) {
        return Book.build(isbn, TITLE, AUTHOR, PRICE);
    }
}
