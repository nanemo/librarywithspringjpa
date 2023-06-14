package com.nanemo.entity;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    private Integer personId;
    @NotEmpty(message = "Release date can't be empty!")
    @Size(min = 2, max = 50)
    private String name;
    private String birthday;
    private List<Book> bookList;
}
