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
import java.util.Iterator;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String viewCart() {
        return "cart";
    }

    @GetMapping("/cart/add")
    public String handleGetAdd() {
        return "redirect:/products";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam(value = "productId", required = false) String productId, 
                            HttpSession session) {
        if (productId == null || productId.trim().isEmpty()) {
            return "redirect:/products";
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct() != null && productId.equals(item.getProduct().getId())) {
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

    // 調整數量：action 為 "increase" 或 "decrease"，扣到 0 自動移除
    @SuppressWarnings("unchecked")
    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam("productId") String productId,
                                 @RequestParam("action") String action,
                                 HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null && productId != null) {
            Iterator<CartItem> iterator = cart.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProduct() != null && productId.equals(item.getProduct().getId())) {
                    if ("increase".equalsIgnoreCase(action)) {
                        item.setQuantity(item.getQuantity() + 1);
                    } else if ("decrease".equalsIgnoreCase(action)) {
                        int newQty = item.getQuantity() - 1;
                        if (newQty <= 0) {
                            iterator.remove(); // 扣到 0 直接移除
                        } else {
                            item.setQuantity(newQty);
                        }
                    }
                    break;
                }
            }
            // 若全部項目都被扣光，直接清空 Session 中的 cart
            if (cart.isEmpty()) {
                session.removeAttribute("cart");
            }
        }
        return "redirect:/cart";
    }

    // 移除單一商品
    @SuppressWarnings("unchecked")
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") String productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null && productId != null) {
            Iterator<CartItem> iterator = cart.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProduct() != null && productId.equals(item.getProduct().getId())) {
                    iterator.remove();
                    break;
                }
            }
            if (cart.isEmpty()) {
                session.removeAttribute("cart");
            }
        }
        return "redirect:/cart";
    }

    // 清空購物車
    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}