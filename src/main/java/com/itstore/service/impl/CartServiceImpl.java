package com.itstore.service.impl;

import com.itstore.dto.CartItemDto;
import com.itstore.dto.CartResponseDto;
import com.itstore.model.Cart;
import com.itstore.model.CartItem;
import com.itstore.model.ServiceProduct;
import com.itstore.service.CartService;
import com.itstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductService productService;

    @Override
    public void addToCart(Cart cart, String productId) {
        if (productId == null || productId.trim().isEmpty()) return;

        // Service 層負責調度其他 Service (如 ProductService)
        ServiceProduct product = productService.getProductById(productId);
        if (product != null) {
            cart.addProduct(product, 1);
        }
    }

    @Override
    public void updateQuantity(Cart cart, String productId, String action) {
        if (productId != null) {
            cart.updateQuantity(productId, action);
        }
    }

    @Override
    public void removeFromCart(Cart cart, String productId) {
        if (productId != null) {
            cart.removeItem(productId);
        }
    }

    @Override
    public CartResponseDto buildCartResponse(Cart cart) {
        List<CartItemDto> itemDtos = new ArrayList<>();
        
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() != null) {
                itemDtos.add(new CartItemDto(
                        item.getProduct().getId(),
                        item.getProduct().getTitle(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getSubtotal()
                ));
            }
        }

        return new CartResponseDto(true, "加入成功", cart.getTotalQuantity(), cart.getTotalPrice(), itemDtos);
    }
}