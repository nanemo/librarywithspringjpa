package com.nanemo.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @NotEmpty(message = "Book name can't be empty!")
    @Size(min = 2, max = 50, message = "Book name must be between from 2 to 50 symbols")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author name can't be empty!")
    @Size(min = 2, max = 50)
    @Column(name = "author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person personName;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;
}
