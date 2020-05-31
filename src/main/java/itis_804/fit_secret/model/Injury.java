package itis_804.fit_secret.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "injuredUsers")
@EqualsAndHashCode(exclude = "injuredUsers")
@Entity
@Table(name = "injury")
public class Injury {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_injury",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> injuredUsers = new HashSet<>();

    public static Injury from(String injuryString) {
        return Injury.builder()
                .name(injuryString)
                .build();
    }

    public static Set<Injury> from(String[] injuries) {
        Set<Injury> injurySet = new HashSet<>();
        for(String injury : injuries) {
            injurySet.add(from(injury));
        }
        return injurySet;
    }

}
