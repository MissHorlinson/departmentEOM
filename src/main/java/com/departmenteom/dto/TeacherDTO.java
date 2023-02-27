package com.departmenteom.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class TeacherDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String passport;
    private String email;
    private String phone;
    private LocalDateTime birthday;
    private String department;
    private long departmentId;
}
