package com.departmenteom.service;

import com.departmenteom.entity.Student;
import com.departmenteom.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepo studentRepo;

    public Student getStudentById(Long id) {
        Optional<Student> studentOpt = studentRepo.findById(id);
        if (studentOpt.isPresent()) {
            return studentOpt.get();
        } else
            throw new IllegalArgumentException("Student with id " + id + " not exist");
    }

    public Student saveStudent(Student student) {
        if (student.getId() != null) {
            log.info("update student");
            student = studentRepo.save(student);
        } else {
            log.info("save student");
            student = studentRepo.save(student);
//            student.setGroupInfo(groupInfoService.getGroupById(student.getGroupInfo().getId()));
        }
        return student;
    }
}
