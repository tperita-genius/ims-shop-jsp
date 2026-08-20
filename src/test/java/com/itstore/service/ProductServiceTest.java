package com.itstore.service;

import com.itstore.dao.ProductDao;
import com.itstore.model.ServiceProduct;
import com.itstore.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDao productDao; // 模擬 DAO，不建立真實資料庫連線

    @InjectMocks
    private ProductServiceImpl productService; // 將 Mock 物件注入到 Service 中

    private ServiceProduct mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = new ServiceProduct(
            "prod-001",
            "Cloud Hosting Plan",
            "Reliable cloud VPS service",
            1200L,
            true,
            LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("測試取得所有上架商品")
    void testGetAllActiveProducts() {
        // Arrange (定義 Mock 行為)
        List<ServiceProduct> mockList = Arrays.asList(mockProduct);
        when(productDao.getAllProducts()).thenReturn(mockList);

        // Act (執行測試方法)
        List<ServiceProduct> result = productService.getAllActiveProducts();

        // Assert (斷言驗證結果)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cloud Hosting Plan", result.get(0).getTitle());
        verify(productDao, times(1)).getAllProducts(); // 驗證 DAO 確實被呼叫過一次
    }

    @Test
    @DisplayName("測試透過 ID 查詢商品")
    void testGetProductById() {
        // Arrange
        when(productDao.getProductById("prod-001")).thenReturn(mockProduct);

        // Act
        ServiceProduct result = productService.getProductById("prod-001");

        // Assert
        assertNotNull(result);
        assertEquals("prod-001", result.getId());
        assertEquals(1200L, result.getPrice());
        verify(productDao, times(1)).getProductById("prod-001");
    }
}