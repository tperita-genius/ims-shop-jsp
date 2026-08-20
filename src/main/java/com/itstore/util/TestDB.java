package com.itstore.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("開始連線測試 Supabase...");
        String sql = "SELECT * FROM products LIMIT 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("✅ 連線成功！你的 products 資料表共有 " + columnCount + " 個欄位：");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("欄位 " + i + ": " + metaData.getColumnName(i) 
                    + " (型別: " + metaData.getColumnTypeName(i) + ")");
            }

            if (rs.next()) {
                System.out.println("\n第一筆資料內容：");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.println("  " + metaData.getColumnName(i) + " = " + rs.getObject(i));
                }
            } else {
                System.out.println("\n⚠️ 資料表目前是空的，沒有資料。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}