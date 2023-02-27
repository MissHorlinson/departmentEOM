package com.departmenteom.dto.dictionary;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudyingTermDTO {
    private Long id;
    private String name;
    private int termInMonthInt;
    private int semesterNum;
}
