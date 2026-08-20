package com.itstore.model;

import java.io.Serializable;

public class ServiceProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String description;
    private long price;
    private boolean active;

    public ServiceProduct() {}

    public ServiceProduct(String id, String title, String description, long price, boolean active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.active = active;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}