package com.departmenteom.web;

import com.departmenteom.dto.StudentDTO;
import com.departmenteom.typemapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentMapper studentMapper;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public StudentDTO saveStudent(@RequestBody StudentDTO student) {
        return studentMapper.saveStudent(student);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public StudentDTO getStudentById(@PathVariable Long id) {
        return studentMapper.getStudentById(id);
    }
}
