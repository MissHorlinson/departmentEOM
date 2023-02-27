package com.departmenteom.dto.dictionary;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuditoryHoursDTO {
    private Long id;
    private String disciplineName;
    private Long disciplineNameId;
    private String disciplineForm;
    private Long disciplineFormId;
    private double hoursNum;
}
