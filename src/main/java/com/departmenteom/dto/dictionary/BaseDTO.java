package com.departmenteom.dto.dictionary;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseDTO {

    private Long id;
    private String name;
}
