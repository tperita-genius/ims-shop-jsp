package com.itstore.controller;

import com.itstore.dto.CartResponseDto;
import com.itstore.model.Cart;
import com.itstore.service.CartService;
import com.itstore.util.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String viewCart() {
        return "cart";
    }

    @GetMapping("/cart/add")
    public String handleGetAdd() {
        return "redirect:/products";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam(value = "productId", required = false) String productId,
            HttpSession session) {
        // 1. Controller 負責管理 Web 專屬的 Session
        Cart cart = SessionUtils.getOrCreate(session, "cart", Cart.class, Cart::new);

        // 2. 將乾淨的領域物件交給 Service 處理
        cartService.addToCart(cart, productId);

        // 3. 處理跳轉
        return "redirect:/cart";
    }

    @PostMapping("/api/cart/add")
    @ResponseBody
    public CartResponseDto apiAddToCart(@RequestParam(value = "productId", required = false) String productId,
            HttpSession session) {
        if (productId == null || productId.trim().isEmpty()) {
            return new CartResponseDto(false, "無效的商品 ID", 0, 0L, new ArrayList<>());
        }

        Cart cart = SessionUtils.getOrCreate(session, "cart", Cart.class, Cart::new);

        cartService.addToCart(cart, productId);

        // DTO 封裝的邏輯也交給 Service
        return cartService.buildCartResponse(cart);
    }

    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam("productId") String productId,
            @RequestParam("action") String action,
            HttpSession session) {
        Cart cart = SessionUtils.getOrCreate(session, "cart", Cart.class, Cart::new);

        cartService.updateQuantity(cart, productId, action);

        if (cart.isEmpty()) {
            session.removeAttribute("cart");
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") String productId, HttpSession session) {
        Cart cart = SessionUtils.getOrCreate(session, "cart", Cart.class, Cart::new);

        cartService.removeFromCart(cart, productId);

        if (cart.isEmpty()) {
            session.removeAttribute("cart");
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}