package com.company.fit_secret.model;

import com.company.fit_secret.model.id.MetricsId;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "metrics")
@IdClass(MetricsId.class)
public class Metrics {
    @Id
    private LocalDateTime date;
    @Id
    private Long userId;
//    @ManyToOne
//    @JoinColumn(name = "userId", referencedColumnName = "userId")
//    private User user;
    private int height;
    private int weight;
    private int OB;
    private int OG;
    private int OT;
}
