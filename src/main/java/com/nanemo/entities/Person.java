package com.nanemo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer personId;

    @NotEmpty(message = "Release date can't be empty!")
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdate;

    @OneToMany(mappedBy = "Owner")
    @Cascade({CascadeType.SAVE_UPDATE,
            CascadeType.REFRESH})
    private List<Book> bookList;
}
