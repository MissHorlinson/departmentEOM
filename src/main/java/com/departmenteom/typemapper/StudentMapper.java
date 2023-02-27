package com.departmenteom.typemapper;

import com.departmenteom.dto.StudentDTO;
import com.departmenteom.entity.GroupInfo;
import com.departmenteom.entity.Student;
import com.departmenteom.service.GroupInfoService;
import com.departmenteom.service.StudentService;
import com.departmenteom.util.DtoToEntityConverter;
import com.departmenteom.util.EntityToDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class StudentMapper {
    private final StudentService studentService;
    private final GroupInfoService groupInfoService;

    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = DtoToEntityConverter.studentToEntity(studentDTO);
        GroupInfo groupInfo = groupInfoService.getGroupById(studentDTO.getGroupId());
        student.setGroupInfo(groupInfo);
        return EntityToDtoConverter.studentToDTO(studentService.saveStudent(student));
    }

    public StudentDTO getStudentById(Long id) {
        return EntityToDtoConverter.studentToDTO(studentService.getStudentById(id));
    }
}
