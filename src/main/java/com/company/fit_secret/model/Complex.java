package com.company.fit_secret.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"injuries","types"})
@EqualsAndHashCode(exclude = {"injuries","types"})
@Entity(name = "complex")
public class Complex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fileName;

    @ManyToMany
    @JoinTable(
            name = "injury_complex",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "injuryId")}
    )
    private Set<Injury> injuries;

    @ManyToMany
    @JoinTable(
            name = "type_complex",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "typeId")}
    )
    private Set<Type> types;
}
