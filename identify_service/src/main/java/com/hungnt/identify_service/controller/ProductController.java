package com.hungnt.identify_service.controller;

import com.hungnt.identify_service.dto.response.ApiResponse;
import com.hungnt.identify_service.entity.Product;
import com.hungnt.identify_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ApiResponse<Product> saveProduct(@RequestBody Product product){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.saveProduct(product));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<Iterable<Product>> getAll(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.getAll());
        return apiResponse;
    }

    @PutMapping("/{id}")
    public ApiResponse<Product> update(@PathVariable String id, @RequestBody Product product){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(productService.update(id, product));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable String id){
        productService.delete(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult("Successfully Deleted");
        return apiResponse;
    }
}
