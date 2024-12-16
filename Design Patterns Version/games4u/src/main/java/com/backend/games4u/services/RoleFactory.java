package com.backend.games4u.services;

import com.backend.games4u.models.ERole;
import com.backend.games4u.models.Role;
import com.backend.games4u.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleFactory {
    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> getRolesFromRequest(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add(getRole(String.valueOf(ERole.ROLE_USER)));
        } else {
            strRoles.forEach(roleName -> roles.add(getRole(roleName)));
        }
        return roles;
    }

    private Role getRole(String roleName) {
        return roleRepository.findByName(ERole.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
}

