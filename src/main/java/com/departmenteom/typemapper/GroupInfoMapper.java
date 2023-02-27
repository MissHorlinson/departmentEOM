package com.departmenteom.typemapper;

import com.departmenteom.dto.GroupInfoDTO;
import com.departmenteom.dto.StudentDTO;
import com.departmenteom.entity.GroupInfo;
import com.departmenteom.service.GroupInfoService;
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
public class GroupInfoMapper {

    private final GroupInfoService groupInfoService;

    public List<GroupInfoDTO> getAllGroup() {
        List<GroupInfo> groupInfoList = groupInfoService.getAllGroup();
        List<GroupInfoDTO> groupInfoDTOList = new ArrayList<>();
        groupInfoList.forEach(item -> groupInfoDTOList.add(EntityToDtoConverter.groupInfoToDto(item)));
        return groupInfoDTOList;
    }

    public GroupInfoDTO saveGroup(GroupInfoDTO groupInfoDTO) {
        GroupInfo groupInfo = DtoToEntityConverter.groupInfoToEntity(groupInfoDTO);
        groupInfo = groupInfoService.saveGroup(groupInfo, groupInfoDTO.getGroupCipherId());
        return EntityToDtoConverter.groupInfoToDto(groupInfo);
    }

    public GroupInfoDTO getGroupById(Long id) {
        return EntityToDtoConverter.groupInfoToDto(groupInfoService.getGroupById(id));
    }

    public List<StudentDTO> getGroupList(Long id) {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        groupInfoService.getGroupList(id).forEach(item -> studentDTOList.add(EntityToDtoConverter.studentToDTO(item)));
        return studentDTOList;
    }
}
