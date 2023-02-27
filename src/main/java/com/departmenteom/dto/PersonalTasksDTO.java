package com.departmenteom.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalTasksDTO {

    private Long id;
    private String disciplineName;
    private Long disciplineNameId;
    private String personalTaskForm;
    private Long personalTaskFormId;
}
