package com.itstore.controller;

import com.itstore.model.CartItem;
import com.itstore.model.ServiceProduct;
import com.itstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    // 傳統表單提交
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam(value = "productId", required = false) String productId, 
                            HttpSession session) {
        processAddToCart(productId, session);
        return "redirect:/cart";
    }

    // AJAX 非同步加入購物車 API
    @PostMapping("/api/cart/add")
    @ResponseBody
    public Map<String, Object> apiAddToCart(@RequestParam(value = "productId", required = false) String productId,
                                           HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (productId == null || productId.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "無效的商品 ID");
            return response;
        }

        processAddToCart(productId, session);

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        int totalQuantity = 0;
        long totalPrice = 0;
        if (cart != null) {
            for (CartItem item : cart) {
                totalQuantity += item.getQuantity();
                totalPrice += item.getSubtotal();
            }
        }

        response.put("success", true);
        response.put("cart", cart);
        response.put("totalQuantity", totalQuantity);
        response.put("totalPrice", totalPrice);
        return response;
    }

    @SuppressWarnings("unchecked")
    private void processAddToCart(String productId, HttpSession session) {
        if (productId == null || productId.trim().isEmpty()) return;

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
    }

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
                            iterator.remove();
                        } else {
                            item.setQuantity(newQty);
                        }
                    }
                    break;
                }
            }
            if (cart.isEmpty()) {
                session.removeAttribute("cart");
            }
        }
        return "redirect:/cart";
    }

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

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}