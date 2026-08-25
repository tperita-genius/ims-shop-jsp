package com.itstore.service;

import com.itstore.dto.CartResponseDto;
import com.itstore.model.Cart;

public interface CartService {
    // 加入商品至購物車
    void addToCart(Cart cart, String productId);
    
    // 更新購物車商品數量
    void updateQuantity(Cart cart, String productId, String action);
    
    // 移除購物車商品
    void removeFromCart(Cart cart, String productId);
    
    // 封裝並建立給前端的 Response DTO
    CartResponseDto buildCartResponse(Cart cart);
}