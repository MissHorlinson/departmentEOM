package com.departmenteom.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportingDTO {
    private Long id;
    private String disciplineReporting;
    private Long disciplineReportingId;
    private String  disciplineReportingForm;
    private Long  disciplineReportingFormId;
}
