package com.nanemo.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @NotEmpty(message = "Book name can't be empty!")
    @Size(min = 2, max = 50)
    @Column(name = "book_name")
    private String bookName;

    @NotEmpty(message = "Author name can't be empty!")
    @Size(min = 2, max = 50)
    @Column(name = "author_name")
    private String authorName;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person personName;
}
