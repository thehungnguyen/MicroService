package com.hungnt.identify_service.repository;

import com.hungnt.identify_service.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductESRepository extends ElasticsearchRepository<Product, String> {
}
