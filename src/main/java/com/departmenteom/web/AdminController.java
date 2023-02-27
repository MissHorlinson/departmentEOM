package com.departmenteom.web;

import com.departmenteom.dto.UserDTO;
import com.departmenteom.typemapper.AdminMapper;
import com.departmenteom.util.Role;
import com.departmenteom.util.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminMapper adminMapper;

    @PostMapping("/user/create")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public UserDTO saveUser(@RequestBody UserDTO usersCred) {
        return adminMapper.saveUserData(usersCred);
    }

    @GetMapping("/user/getByUsername/{username}")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public UserDTO getUserByUsername(@PathVariable String username) {
        return adminMapper.getUserByUsername(username);
    }

    @GetMapping("/role/all")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public List<Role> getRoleList() {
        return List.of(Role.values());
    }

    @GetMapping("/status/all")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public List<Status> getStatusList() {
        return List.of(Status.values());
    }

    @GetMapping("/user/all")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public List<UserDTO> getUserList() {
        return adminMapper.getAllUserList();
    }
}
