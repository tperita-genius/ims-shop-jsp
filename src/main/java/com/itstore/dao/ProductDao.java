package com.itstore.dao;

import com.itstore.model.ServiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate; // 由 Spring 自動注入

    private final RowMapper<ServiceProduct> rowMapper = new RowMapper<ServiceProduct>() {
        @Override
        public ServiceProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            Timestamp ts = rs.getTimestamp("created_at");
            LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : null;
            return new ServiceProduct(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getLong("price"),
                rs.getBoolean("is_active"),
                createdAt
            );
        }
    };

    public List<ServiceProduct> getAllProducts() {
        String sql = "SELECT id, title, description, price, is_active, created_at FROM products WHERE is_active = true ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public ServiceProduct getProductById(String id) {
        String sql = "SELECT id, title, description, price, is_active, created_at FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}