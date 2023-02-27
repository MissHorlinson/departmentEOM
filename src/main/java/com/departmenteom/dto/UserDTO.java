package com.departmenteom.dto;

import com.departmenteom.util.Role;
import com.departmenteom.util.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private Role role;
    private Status status;
    private String password;
}
