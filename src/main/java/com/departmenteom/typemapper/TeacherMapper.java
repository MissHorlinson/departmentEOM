package com.departmenteom.typemapper;

import com.departmenteom.dto.TeacherDTO;
import com.departmenteom.entity.Teacher;
import com.departmenteom.entity.dictionary.Department;
import com.departmenteom.service.TeacherService;
import com.departmenteom.service.UtilDictionaryService;
import com.departmenteom.util.DtoToEntityConverter;
import com.departmenteom.util.EntityToDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class TeacherMapper {
    private final TeacherService teacherService;
    private final UtilDictionaryService utilDictionaryService;

    public TeacherDTO saveTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = DtoToEntityConverter.teacherToEntity(teacherDTO);
        Department department = utilDictionaryService.getDepartmentById(teacherDTO.getDepartmentId());
        teacher.setDepartmentInfo(department);
        return EntityToDtoConverter.teacherToDTO(teacherService.saveTeacher(teacher));
    }

    public TeacherDTO getTeacherById(Long id) {
        return EntityToDtoConverter.teacherToDTO(teacherService.getTeacherById(id));
    }

    public List<TeacherDTO> getTeacherList() {
        List<Teacher> teacherList = teacherService.getTeacherList();
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        teacherList.forEach(item -> teacherDTOList.add(EntityToDtoConverter.teacherToDTO(item)));
        return teacherDTOList;
    }
}
