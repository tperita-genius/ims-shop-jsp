package com.itstore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemTest {

    @Test
    @DisplayName("測試購物車小計金額運算")
    void testCartItemSubtotal() {
        ServiceProduct product = new ServiceProduct(
            "prod-002",
            "Domain Name (.com)",
            "1 Year registration",
            450L,
            true,
            LocalDateTime.now()
        );

        CartItem item = new CartItem(product, 3);

        // 單價 450 * 數量 3 = 1350
        assertEquals(1350L, item.getSubtotal());
    }

    @Test
    @DisplayName("測試購物車商品為空時小計回傳 0")
    void testSubtotalWithNullProduct() {
        CartItem item = new CartItem(null, 2);
        assertEquals(0L, item.getSubtotal());
    }
}