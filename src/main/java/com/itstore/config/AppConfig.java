package com.itstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.itstore")
public class AppConfig implements WebMvcConfigurer {

    // 1. 註冊 JSP ViewResolver 視圖解析器
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    // 2. 註冊 DataSource (優先讀取 Render 環境變數，次之讀本地 db.properties)
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (in != null) {
                    Properties prop = new Properties();
                    prop.load(in);
                    url = prop.getProperty("db.url");
                    user = prop.getProperty("db.user");
                    password = prop.getProperty("db.password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        return ds;
    }

    // 3. 註冊 JdbcTemplate (Spring 官方資料庫存取工具)
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // 4. 靜態資源映射 (讓 CSS/JS/圖片不被攔截)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
}