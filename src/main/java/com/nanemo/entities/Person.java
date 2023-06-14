package com.nanemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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

    @Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime birthdate;

    @OneToMany(mappedBy = "personName")
    @Cascade({CascadeType.SAVE_UPDATE,
            CascadeType.DELETE,
            CascadeType.REFRESH})
    private List<Book> bookList;
}
