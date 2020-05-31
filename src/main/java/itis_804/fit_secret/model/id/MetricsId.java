package itis_804.fit_secret.model.id;

import itis_804.fit_secret.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricsId implements Serializable {
//    private User user;
    private Long userId;
    private LocalDateTime date;
}
