package com.departmenteom.dto.dictionary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisciplineTypeDTO {

    private Long id;
    private String name;
}
