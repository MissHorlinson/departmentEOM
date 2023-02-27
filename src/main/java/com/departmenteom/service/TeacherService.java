package com.departmenteom.service;

import com.departmenteom.entity.Teacher;
import com.departmenteom.repo.TeacherRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final TeacherRepo teacherRepo;

    public Teacher getTeacherById(Long id) {
        Optional<Teacher> teacherOpt = teacherRepo.findById(id);
        if (teacherOpt.isPresent()) {
            return teacherOpt.get();
        } else
            throw new IllegalArgumentException("Teacher with id " + id + " not exist");
    }

    public Teacher saveTeacher(Teacher teacher) {
        if (teacher.getId() != null) {
            log.info("Update teacher");
            teacher = teacherRepo.save(teacher);
        } else {
            log.info("Save teacher");
            teacher = teacherRepo.save(teacher);
        }
        return teacher;
    }

    public List<Teacher> getTeacherList() {
       return teacherRepo.findAll();
    }
}
