package com.company.fit_secret.dto;

import com.company.fit_secret.model.Metrics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturningMetricsDto {

    private LocalDate date;

    private int height;
    private int weight;
    private int OB;
    private int OG;
    private int OT;

    public static ReturningMetricsDto from(Metrics metrics) {
        return ReturningMetricsDto.builder()
                .date(metrics.getDate().toLocalDate())
                .height(metrics.getHeight())
                .weight(metrics.getWeight())
                .OB(metrics.getOB())
                .OG(metrics.getOG())
                .OT(metrics.getOT())
                .build();
    }

}
