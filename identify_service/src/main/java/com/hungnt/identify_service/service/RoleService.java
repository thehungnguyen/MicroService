package com.hungnt.identify_service.service;

import com.hungnt.identify_service.dto.request.RoleRequest;
import com.hungnt.identify_service.dto.response.RoleResponse;
import com.hungnt.identify_service.entity.Role;
import com.hungnt.identify_service.repository.PermissionRepository;
import com.hungnt.identify_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = new Role();

        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);

        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        roleResponse.setPermissions(role.getPermissions());

        return roleResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponse> roleResponses = new ArrayList<>();

        for (Role r : roles) {
            RoleResponse roleResponse = new RoleResponse();

            roleResponse.setName(r.getName());
            roleResponse.setDescription(r.getDescription());
            roleResponse.setPermissions(r.getPermissions());

            roleResponses.add(roleResponse);
        }

        return roleResponses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRole(String name) {
        roleRepository.deleteById(name);
    }
}
