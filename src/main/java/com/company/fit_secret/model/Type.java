package com.company.fit_secret.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "complexes")
@EqualsAndHashCode(exclude = "complexes")
@Entity(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;
    private String type;

    @ManyToMany(mappedBy = "types")
    private Set<Complex> complexes = new HashSet<>();
}
