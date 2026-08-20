package com.itstore.controller;

import com.itstore.model.CartItem;
import com.itstore.model.ServiceProduct;
import com.itstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String viewCart() {
        return "cart"; // 對應 /WEB-INF/views/cart.jsp
    }
    
    @SuppressWarnings("unchecked")
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") String productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found) {
            ServiceProduct product = productService.getProductById(productId);
            if (product != null) {
                cart.add(new CartItem(product, 1));
            }
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}