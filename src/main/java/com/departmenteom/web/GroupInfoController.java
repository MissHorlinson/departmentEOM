package com.departmenteom.web;

import com.departmenteom.dto.GroupInfoDTO;
import com.departmenteom.dto.StudentDTO;
import com.departmenteom.typemapper.GroupInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/groupInfo")
public class GroupInfoController {
    private final GroupInfoMapper groupInfoMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<GroupInfoDTO> getAllGroup() {
        return groupInfoMapper.getAllGroup();
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public GroupInfoDTO saveGroup(@RequestBody GroupInfoDTO groupInfo) {
        return groupInfoMapper.saveGroup(groupInfo);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public GroupInfoDTO getGroupById(@PathVariable Long id) {
        return groupInfoMapper.getGroupById(id);
    }

    @GetMapping("/getGroupList/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<StudentDTO> getStudentsList(@PathVariable Long id) {
        return groupInfoMapper.getGroupList(id);
    }
}
