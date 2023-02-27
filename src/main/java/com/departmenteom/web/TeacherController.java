package com.departmenteom.web;

import com.departmenteom.dto.TeacherDTO;
import com.departmenteom.typemapper.TeacherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherMapper teacherMapper;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public TeacherDTO saveTeacher(@RequestBody TeacherDTO teacher) {
        return teacherMapper.saveTeacher(teacher);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public TeacherDTO getTeacherById(@PathVariable Long id) {
        return teacherMapper.getTeacherById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<TeacherDTO> getTeacherList() {
        return teacherMapper.getTeacherList();
    }
}

