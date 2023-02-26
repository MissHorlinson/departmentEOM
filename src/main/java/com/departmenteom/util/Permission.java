package com.departmenteom.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    DEPARTMENT_READ("depart:read"),
    DEPARTMENT_WRITE("depart:write");
    private final String permission;
}
