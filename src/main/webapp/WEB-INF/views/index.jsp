<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>IT Store SSR - 現代化服務電商平台</title>
    <!-- Font Awesome -->
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
            基於 <strong>Spring Framework 5</strong> 與 <strong>JSP/JSTL</strong> 建構的高效能伺服器端渲染（SSR）電商系統。資料持久層透過 <strong>Spring JdbcTemplate</strong> 串接 <strong>Supabase 雲端 PostgreSQL</strong> 資料庫。點擊下方按鈕即可瀏覽服務型產品並體驗購物流程。
        </p>

        <div class="tech-grid">
            <div class="tech-card">
                <i class="fa-brands fa-envira"></i>
                <div class="tech-label">核心架構</div>
                <div class="tech-value">Spring Framework 5</div>
            </div>
            <div class="tech-card">
                <i class="fa-solid fa-layer-group"></i>
                <div class="tech-label">視圖渲染 (SSR)</div>
                <div class="tech-value">JSP 2.3 / JSTL 1.2</div>
            </div>
            <div class="tech-card">
                <i class="fa-solid fa-cube"></i>
                <div class="tech-label">持久層技術</div>
                <div class="tech-value">Spring JdbcTemplate</div>
            </div>
            <div class="tech-card">
                <i class="fa-solid fa-database"></i>
                <div class="tech-label">資料庫</div>
                <div class="tech-value">Supabase DB (Postgres)</div>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/products" class="btn-enter">
            進入服務商品專區
            <i class="fa-solid fa-arrow-right"></i>
        </a>
    </div>

</body>
</html>