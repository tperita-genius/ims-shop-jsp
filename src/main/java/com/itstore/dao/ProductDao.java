package com.itstore.dao;

import com.itstore.model.ServiceProduct;
import com.itstore.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    // 取得所有上架中 (is_active = true) 的商品
    public List<ServiceProduct> getAllProducts() {
        List<ServiceProduct> list = new ArrayList<>();
        String sql = "SELECT id, title, description, price, is_active FROM products WHERE is_active = true ORDER BY created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ServiceProduct p = new ServiceProduct(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getLong("price"),
                    rs.getBoolean("is_active")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 根據 UUID 取得單一商品
    public ServiceProduct getProductById(String id) {
        String sql = "SELECT id, title, description, price, is_active FROM products WHERE id::text = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ServiceProduct(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("price"),
                        rs.getBoolean("is_active")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}