package com.ks.EventManagement.entity.authority;

import static com.ks.EventManagement.entity.authority.Permissions.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Set.of(WRITE, READ, UPDATE, DELETE)),
    ADMIN(Set.of(WRITE, READ, UPDATE, DELETE)),
    ANYKEY(Set.of(WRITE, READ, UPDATE, DELETE)),
    ROOT(Set.of(WRITE, READ, UPDATE, DELETE));

    public final Set<Permissions> setPermissions;

    public List<SimpleGrantedAuthority> authority(){
        var authority = new ArrayList<SimpleGrantedAuthority>(setPermissions
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name()))
                .toList()
        );
        authority.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authority;
    }
}
