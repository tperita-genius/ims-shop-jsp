package com.itstore.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Class.forName("org.postgresql.Driver");

            // 1. 優先從系統環境變數讀取 (Render 雲端環境)
            url = System.getenv("DB_URL");
            user = System.getenv("DB_USER");
            password = System.getenv("DB_PASSWORD");

            // 2. 若環境變數不存在，則讀取本地 db.properties
            if (url == null || user == null || password == null) {
                try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
                    if (input != null) {
                        Properties prop = new Properties();
                        prop.load(input);
                        url = prop.getProperty("db.url");
                        user = prop.getProperty("db.user");
                        password = prop.getProperty("db.password");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (url == null || user == null || password == null) {
            throw new SQLException("資料庫連線資訊未設定（找不到環境變數或 db.properties）");
        }
        return DriverManager.getConnection(url, user, password);
    }
}