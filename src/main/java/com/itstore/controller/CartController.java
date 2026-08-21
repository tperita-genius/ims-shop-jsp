package com.itstore.controller;

import com.itstore.dto.CartItemDto;
import com.itstore.dto.CartResponseDto;
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

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam(value = "productId", required = false) String productId, 
                            HttpSession session) {
        processAddToCart(productId, session);
        return "redirect:/cart";
    }

    // AJAX API：回傳強型別 CartResponseDto
    @PostMapping("/api/cart/add")
    @ResponseBody
    public CartResponseDto apiAddToCart(@RequestParam(value = "productId", required = false) String productId,
                                        HttpSession session) {
        if (productId == null || productId.trim().isEmpty()) {
            return new CartResponseDto(false, "無效的商品 ID", 0, 0L, new ArrayList<>());
        }

        processAddToCart(productId, session);

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        
        List<CartItemDto> itemDtos = new ArrayList<>();
        int totalQuantity = 0;
        long totalPrice = 0;

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct() != null) {
                    itemDtos.add(new CartItemDto(
                        item.getProduct().getId(),
                        item.getProduct().getTitle(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getSubtotal()
                    ));
                    totalQuantity += item.getQuantity();
                    totalPrice += item.getSubtotal();
                }
            }
        }

        return new CartResponseDto(true, "加入成功", totalQuantity, totalPrice, itemDtos);
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