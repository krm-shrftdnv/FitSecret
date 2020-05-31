package com.company.fit_secret.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "injuries")
@EqualsAndHashCode(exclude = "injuries")
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String fullName;
    private int age;

    @Column(unique = true)
    private String email;
    private String password;

    @ManyToMany(mappedBy = "injuredUsers")
    private Set<Injury> injuries = new HashSet<>();

//    @OneToMany(mappedBy = "user")
//    private Set<Metrics> metrics;

}
