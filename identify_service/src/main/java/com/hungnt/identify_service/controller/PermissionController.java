package com.hungnt.identify_service.controller;

import com.hungnt.identify_service.dto.request.PermissionRequest;
import com.hungnt.identify_service.dto.response.ApiResponse;
import com.hungnt.identify_service.dto.response.PermissionResponse;
import com.hungnt.identify_service.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/permissions")
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(permissionService.createPermission(permissionRequest));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAllPermission() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(permissionService.getAllPermissions());
        return apiResponse;
    }

    @DeleteMapping("/{permissionName}")
    public ApiResponse<String> deletePermission(@PathVariable String permissionName) {
        permissionService.deletePermission(permissionName);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult("Permission has been deleted");
        return apiResponse;
    }
}
