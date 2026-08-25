package com.itstore.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    // 1. 新增商品或增加數量
    public void addProduct(ServiceProduct product, int quantity) {
        if (product == null || product.getId() == null) return;

        // 如果已存在，直接增加數量
        for (CartItem item : items) {
            if (item.getProduct() != null && product.getId().equals(item.getProduct().getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // 如果不存在，新增一筆
        items.add(new CartItem(product, quantity));
    }

    // 2. 更新商品數量 (增減)
    public void updateQuantity(String productId, String action) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct() != null && productId.equals(item.getProduct().getId())) {
                if ("increase".equalsIgnoreCase(action)) {
                    item.setQuantity(item.getQuantity() + 1);
                } else if ("decrease".equalsIgnoreCase(action)) {
                    int newQty = item.getQuantity() - 1;
                    if (newQty <= 0) {
                        iterator.remove(); // 數量小於等於 0 則移除
                    } else {
                        item.setQuantity(newQty);
                    }
                }
                break; // 找到了就提早結束迴圈
            }
        }
    }

    // 3. 移除特定商品
    public void removeItem(String productId) {
        // 使用 Java 8 的 removeIf，語法更簡潔，不需要寫 Iterator 迴圈
        items.removeIf(item -> item.getProduct() != null && productId.equals(item.getProduct().getId()));
    }

    // 4. 計算總件數
    public int getTotalQuantity() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    // 5. 計算總金額
    public long getTotalPrice() {
        return items.stream().mapToLong(CartItem::getSubtotal).sum();
    }
    
    // 6. 判斷是否為空
    public boolean isEmpty() {
        return items.isEmpty();
    }
}