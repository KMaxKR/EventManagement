package com.ks.EventManagement.entity.authority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permissions {
    WRITE("WRITE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    public final String getPermissions;
}
