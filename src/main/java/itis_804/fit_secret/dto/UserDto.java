package itis_804.fit_secret.dto;

import itis_804.fit_secret.model.Injury;
import itis_804.fit_secret.model.User;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "injuries")
@Data
public class UserDto {

    private Long userId;
    private String fullName;
    private int age;
    private String email;
    private Set<Injury> injuries;

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .age(user.getAge())
                .injuries(user.getInjuries())
                .email(user.getEmail())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

}
