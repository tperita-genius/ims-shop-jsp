package com.itstore.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class AppStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired(required = false)
    private ServletContext servletContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null || servletContext != null) {
            String contextPath = (servletContext != null) ? servletContext.getContextPath() : "/it-shop-ssr";
            String port = System.getProperty("server.port", "8080");

            System.out.println("\n==================================================================");
            System.out.println("  [SUCCESS] IT Store SSR 應用程式啟動成功！");
            System.out.println("------------------------------------------------------------------");
            System.out.println("  * 首頁預覽 (Landing Page):");
            System.out.println("    http://localhost:" + port + contextPath + "/");
            System.out.println("  * 商品列表 (Product Catalog):");
            System.out.println("    http://localhost:" + port + contextPath + "/products");
            System.out.println("  * 購物車管理 (Cart View):");
            System.out.println("    http://localhost:" + port + contextPath + "/cart");
            System.out.println("==================================================================\n");
        }
    }
}