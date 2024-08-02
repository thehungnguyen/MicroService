package com.hungnt.identify_service.controller;

import com.hungnt.identify_service.dto.request.RoleRequest;
import com.hungnt.identify_service.dto.response.ApiResponse;
import com.hungnt.identify_service.dto.response.RoleResponse;
import com.hungnt.identify_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/roles")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(roleService.createRole(roleRequest));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRole() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(roleService.getAllRoles());
        return apiResponse;
    }

    @DeleteMapping("/{roleName}")
    public ApiResponse<String> deleteRole(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult("Role has been deleted");
        return apiResponse;
    }
}
