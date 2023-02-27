package com.departmenteom.dto.dictionary;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudyingTypeDTO {
    private Long id;
    private String name;
    private String letter;
}
