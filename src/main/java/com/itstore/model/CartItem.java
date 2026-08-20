package com.itstore.model;

import java.io.Serializable;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private ServiceProduct product;
    private int quantity;

    public CartItem() {}

    // 補上這個建構子
    public CartItem(ServiceProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ServiceProduct getProduct() {
        return product;
    }

    public void setProduct(ServiceProduct product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getSubtotal() {
        return product != null ? product.getPrice() * quantity : 0;
    }
}