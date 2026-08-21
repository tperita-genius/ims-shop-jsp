package com.itstore.dto;

import java.util.List;

public class CartResponseDto {
    private boolean success;
    private String message;
    private int totalQuantity;
    private Long totalPrice;
    private List<CartItemDto> items;

    public CartResponseDto() {}

    public CartResponseDto(boolean success, String message, int totalQuantity, Long totalPrice, List<CartItemDto> items) {
        this.success = success;
        this.message = message;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public Long getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Long totalPrice) { this.totalPrice = totalPrice; }

    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }
}