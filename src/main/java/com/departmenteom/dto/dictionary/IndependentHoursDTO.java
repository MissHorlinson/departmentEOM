package com.departmenteom.dto.dictionary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndependentHoursDTO {

    private Long id;
    private String disciplineName;
    private double hoursNum;
}
