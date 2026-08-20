package com.itstore.service.impl;

import com.itstore.dao.ProductDao;
import com.itstore.model.ServiceProduct;
import com.itstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao; // DI 注入 DAO

    @Override
    public List<ServiceProduct> getAllActiveProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public ServiceProduct getProductById(String id) {
        return productDao.getProductById(id);
    }
}