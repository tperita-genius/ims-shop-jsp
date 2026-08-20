package com.itstore.controller;

import com.itstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // 首頁：技術展示導覽頁
    @GetMapping(value = {"", "/"})
    public String index() {
        return "index"; // 對應 /WEB-INF/views/index.jsp
    }

    // 商品頁：服務商品列表
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllActiveProducts());
        return "products"; // 對應 /WEB-INF/views/products.jsp
    }
}