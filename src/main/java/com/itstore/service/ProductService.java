package com.itstore.service;

import com.itstore.model.ServiceProduct;
import java.util.List;

public interface ProductService {
    List<ServiceProduct> getAllActiveProducts();
    ServiceProduct getProductById(String id);
}