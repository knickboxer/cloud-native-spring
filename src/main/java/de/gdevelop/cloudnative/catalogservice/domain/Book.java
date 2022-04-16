package de.gdevelop.cloudnative.catalogservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * @author Gerhard
 */
public record Book(
        @Id
        Long id,

        @NotBlank(message = "The book ISBN must be defined.")
        //@ISBN(message = "The ISBN is not valid.")
        @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must follow the standards ISBN-10 or ISBN-13.")
        String isbn,
        @NotBlank(message = "The book title must be defined.")
        String title,
        @NotBlank(message = "The book author must be defined.")
        String author,
        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be greater than zero.")
        Double price,

        @Version
        int version
) {
        public static Book build(String isbn, String title, String author, Double price) {
                return new Book (null, isbn, title, author, price, 0);
        }

}
