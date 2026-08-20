<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>IT Store SSR - 現代化服務電商平台</title>
    <!-- Font Awesome 6 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <!-- 靜態資源引入 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
</head>
<body class="page-index">

    <div class="container">
        <div class="badge">
            <i class="fa-solid fa-code-branch"></i>
            Spring MVC SSR Architecture v1.0
        </div>
        
        <h1>歡迎使用 IT 服務電商平台</h1>
        
        <p class="description">
            基於 <strong>Spring Framework 5.3</strong> 與 <strong>Java 11</strong> 建構的高效能伺服器端渲染（SSR）電商系統。嚴格實踐三層式解耦架構，資料持久層透過 <strong>Spring JdbcTemplate</strong> 串接 <strong>Supabase 雲端 PostgreSQL</strong> 資料庫，並導入 <strong>JUnit 5 + Mockito</strong> 單元測試。
        </p>

        <div class="tech-grid">
            <div class="tech-card">
                <i class="fa-brands fa-java"></i>
                <div class="tech-label">核心語言與框架</div>
                <div class="tech-value">Java 11 + Spring 5.3</div>
                <div class="tech-sub">IoC 容器 / DI 依賴注入</div>
            </div>

            <div class="tech-card">
                <i class="fa-solid fa-layer-group"></i>
                <div class="tech-label">伺服器端渲染 (SSR)</div>
                <div class="tech-value">JSP 2.3 + JSTL 1.2</div>
                <div class="tech-sub">Spring MVC / DispatcherServlet</div>
            </div>

            <div class="tech-card">
                <i class="fa-solid fa-cube"></i>
                <div class="tech-label">資料持久層</div>
                <div class="tech-value">Spring JdbcTemplate</div>
                <div class="tech-sub">連線池管理 / 參數化防注入</div>
            </div>

            <div class="tech-card">
                <i class="fa-solid fa-database"></i>
                <div class="tech-label">雲端資料庫</div>
                <div class="tech-value">Supabase DB (Postgres)</div>
                <div class="tech-sub">PostgreSQL 16 / UUID 主鍵</div>
            </div>

            <div class="tech-card">
                <i class="fa-solid fa-vial-circle-check"></i>
                <div class="tech-label">單元測試體系</div>
                <div class="tech-value">JUnit 5 + Mockito 5</div>
                <div class="tech-sub">Service 隔離驗證 / DAO Mocking</div>
            </div>

            <div class="tech-card">
                <i class="fa-solid fa-box-open"></i>
                <div class="tech-label">建置與部署</div>
                <div class="tech-value">Apache Maven + Tomcat 9</div>
                <div class="tech-sub">WAR 打包 / Render CI/CD 自動化</div>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/products" class="btn-enter">
            進入服務商品專區
            <i class="fa-solid fa-arrow-right"></i>
        </a>
    </div>

</body>
</html>