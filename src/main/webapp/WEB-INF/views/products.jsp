<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>IT 服務商品清單 - IT Store</title>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <!-- 靜態資源引入 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/products.css">
</head>
<body class="page-products">

    <header class="app-header">
        <a href="${pageContext.request.contextPath}/" class="logo">
            <i class="fa-solid fa-cloud"></i> IT Store
        </a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/cart" class="btn-cart">
                <i class="fa-solid fa-cart-shopping"></i> 查看購物車
            </a>
        </div>
    </header>

    <main>
        <div class="page-header">
            <h2>IT 服務與雲端方案</h2>
            <p>全系列架構由 Spring MVC 驅動，資料即時由雲端資料庫檢索呈現</p>
        </div>

        <div class="product-grid">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
                    <div>
                        <div class="product-icon">
                            <i class="fa-solid fa-server"></i>
                        </div>
                        <h3 class="product-title">${product.title}</h3>
                        <p class="product-desc">${product.description}</p>
                    </div>

                    <div class="product-footer">
                        <div class="price">
                            NT$ <fmt:formatNumber value="${product.price}" pattern="#,###" />
                            <small>/ 週期</small>
                        </div>
                        <form action="${pageContext.request.contextPath}/cart/add" method="POST">
                            <input type="hidden" name="productId" value="${product.id}" />
                            <button type="submit" class="btn-add">
                                <i class="fa-solid fa-plus"></i> 加入
                            </button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </main>

</body>
</html>