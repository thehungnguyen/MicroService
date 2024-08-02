package com.hungnt.identify_service.service;

import com.hungnt.identify_service.dto.request.PermissionRequest;
import com.hungnt.identify_service.dto.response.PermissionResponse;
import com.hungnt.identify_service.entity.Permission;
import com.hungnt.identify_service.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = new Permission();
        permission.setName(permissionRequest.getName());
        permission.setDescription(permissionRequest.getDescription());
        permissionRepository.save(permission);

        PermissionResponse permissionResponse = new PermissionResponse();
        permissionResponse.setName(permission.getName());
        permissionResponse.setDescription(permission.getDescription());
        return permissionResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionResponse> permissionResponses = new ArrayList<>();

        for (Permission p : permissions) {
            PermissionResponse permissionResponse = new PermissionResponse();
            permissionResponse.setName(p.getName());
            permissionResponse.setDescription(p.getDescription());

            permissionResponses.add(permissionResponse);
        }

        return permissionResponses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(String name) {
        permissionRepository.deleteById(name);
    }
}
