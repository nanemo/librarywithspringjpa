package com.nanemo.entity;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Integer bookId;
    @NotEmpty(message = "Book name can't be empty!")
    @Size(min = 2, max = 50)
    private String bookName;
    @NotEmpty(message = "Author name can't be empty!")
    @Size(min = 2, max = 50)
    private String authorName;
    @Pattern(regexp = "\\d{4}", message = "Please enter correct year")
    private String birthday;
    private String name;
}
