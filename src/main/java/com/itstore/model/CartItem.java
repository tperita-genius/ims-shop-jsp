package com.itstore.model;

import java.io.Serializable;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private ServiceProduct product;
    private int quantity;
    private String customRequirement;

    public CartItem() {}

    public CartItem(ServiceProduct product, int quantity, String customRequirement) {
        this.product = product;
        this.quantity = quantity;
        this.customRequirement = customRequirement;
    }

    public ServiceProduct getProduct() { return product; }
    public void setProduct(ServiceProduct product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCustomRequirement() { return customRequirement; }
    public void setCustomRequirement(String customRequirement) { this.customRequirement = customRequirement; }

    public long getSubtotal() {
        return product != null ? product.getPrice() * quantity : 0;
    }
}