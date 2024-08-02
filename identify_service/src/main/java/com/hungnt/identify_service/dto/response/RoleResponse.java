package com.hungnt.identify_service.dto.response;

import com.hungnt.identify_service.entity.Permission;

import java.util.Set;

public class RoleResponse {
    private String name;
    private String description;
    private Set<Permission> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
