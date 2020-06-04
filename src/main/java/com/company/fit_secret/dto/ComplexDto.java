package com.company.fit_secret.dto;

import com.company.fit_secret.model.Complex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplexDto {
    private Long id;
    private String name;
    private String description;

    public static ComplexDto from(Complex complex, String description){
        return ComplexDto.builder()
                .id(complex.getId())
                .name(complex.getName())
                .description(description)
                .build();
    }

}
