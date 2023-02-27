package com.departmenteom.typemapper;

import com.departmenteom.dto.UserDTO;
import com.departmenteom.entity.UsersCred;
import com.departmenteom.security.UserDetailsServiceImpl;
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
public class AdminMapper {

    private final UserDetailsServiceImpl userDetailsService;

    public UserDTO saveUserData(UserDTO usersCred) {
        log.info("save new user data");
        UsersCred newUser = DtoToEntityConverter.userCredToEntity(usersCred);
        newUser = userDetailsService.saveUser(newUser);
        return EntityToDtoConverter.userToDTO(newUser);
    }

    public List<UserDTO> getAllUserList() {
        List<UserDTO> userList = new ArrayList<>();
        userDetailsService.getAllUsers().forEach(item -> userList.add(EntityToDtoConverter.userToDTO(item)));
        return userList;
    }

    public UserDTO getUserByUsername(String username) {
        return EntityToDtoConverter.userToDTO(userDetailsService.getUserByUsername(username));
    }
}
