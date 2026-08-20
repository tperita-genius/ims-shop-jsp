package com.itstore.dao;

import com.itstore.model.ServiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ServiceProduct> productRowMapper = new RowMapper<ServiceProduct>() {
        @Override
        public ServiceProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            ServiceProduct p = new ServiceProduct();
            p.setId(rs.getString("id"));
            p.setTitle(rs.getString("title"));
            p.setDescription(rs.getString("description"));
            p.setPrice(rs.getLong("price"));
            p.setIsActive(rs.getBoolean("is_active"));
            
            java.sql.Timestamp ts = rs.getTimestamp("created_at");
            if (ts != null) {
                p.setCreatedAt(ts.toLocalDateTime());
            }
            return p;
        }
    };

    public List<ServiceProduct> getAllProducts() {
        // 使用 products 資料表
        String sql = "SELECT id, title, description, price, is_active, created_at FROM products WHERE is_active = true ORDER BY created_at ASC";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public ServiceProduct getProductById(String id) {
        // 在 ? 後方加上 ::uuid 進行強制型別轉換
        String sql = "SELECT id, title, description, price, is_active, created_at FROM products WHERE id = ?::uuid";
        List<ServiceProduct> list = jdbcTemplate.query(sql, productRowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }
}