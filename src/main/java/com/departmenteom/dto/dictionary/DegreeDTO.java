package com.departmenteom.dto.dictionary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DegreeDTO {

    private Long id;
    private String name;
}
