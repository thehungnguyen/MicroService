package com.hungnt.identify_service.service;

import com.hungnt.identify_service.entity.Product;
import com.hungnt.identify_service.exception.AppException;
import com.hungnt.identify_service.exception.ErrorCode;
import com.hungnt.identify_service.repository.ProductESRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductESRepository productESRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Product saveProduct(Product product){ return productESRepository.save(product);}

    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<Product> getAll(){
        return productESRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Product update(String id, Product product){
        Product p = productESRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());

        return productESRepository.save(p);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id){
        productESRepository.deleteById(id);
    }
}
