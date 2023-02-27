package com.departmenteom.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class StudentDTO {
        private Long id;
        private String firstName;
        private String secondName;
        private String lastName;
        private String passport;
        private String email;
        private String phone;
        private LocalDateTime birthday;
        private String recordBook;
        private String groupName;
        private Long groupId;
}



