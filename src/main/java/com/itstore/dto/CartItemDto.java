package com.itstore.dto;

public class CartItemDto {
    private String productId;
    private String title;
    private Long price;
    private Integer quantity;
    private Long subtotal;

    public CartItemDto() {}

    public CartItemDto(String productId, String title, Long price, Integer quantity, Long subtotal) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Long getSubtotal() { return subtotal; }
    public void setSubtotal(Long subtotal) { this.subtotal = subtotal; }
}