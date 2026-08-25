<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>購物車 - IT Store</title>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <!-- 靜態資源引入 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/cart.css">
</head>
<body class="page-cart">

    <header class="app-header">
        <a href="${pageContext.request.contextPath}/" class="logo">
            <i class="fa-solid fa-cloud"></i> IT Store
        </a>
    </header>

    <main>
        <div class="cart-card">
            <div class="cart-header">
                <h2><i class="fa-solid fa-bag-shopping"></i> 購物車內容</h2>
                <c:if test="${not empty sessionScope.cart.items}">
                    <form action="${pageContext.request.contextPath}/cart/clear" method="POST" style="margin: 0;">
                        <button type="submit" class="btn-clear" onclick="return confirm('確定要清空購物車嗎？');">
                            <i class="fa-solid fa-trash-can"></i> 清空購物車
                        </button>
                    </form>
                </c:if>
            </div>

            <c:choose>
                <c:when test="${empty sessionScope.cart.items}">
                    <div class="empty-state">
                        <i class="fa-solid fa-cart-arrow-down"></i>
                        <p>購物車目前是空的，快去選購服務吧！</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>服務名稱</th>
                                <th>單價</th>
                                <th style="text-align: center; width: 140px;">數量</th>
                                <th style="text-align: right;">小計</th>
                                <th style="text-align: center; width: 70px;">移除</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${sessionScope.cart.items}">
                                <tr>
                                    <td style="font-weight: 600; color: #0f172a;">${item.product.title}</td>
                                    <td>NT$ <fmt:formatNumber value="${item.product.price}" pattern="#,###" /></td>
                                    <td style="text-align: center;">
                                        <div class="qty-control">
                                            <!-- 減號按鈕 -->
                                            <form action="${pageContext.request.contextPath}/cart/update" method="POST" style="margin: 0;">
                                                <input type="hidden" name="productId" value="${item.product.id}" />
                                                <input type="hidden" name="action" value="decrease" />
                                                <button type="submit" class="btn-qty" title="減少數量">
                                                    <i class="fa-solid fa-minus"></i>
                                                </button>
                                            </form>
                                            <span class="qty-display">${item.quantity}</span>
                                            <!-- 加號按鈕 -->
                                            <form action="${pageContext.request.contextPath}/cart/update" method="POST" style="margin: 0;">
                                                <input type="hidden" name="productId" value="${item.product.id}" />
                                                <input type="hidden" name="action" value="increase" />
                                                <button type="submit" class="btn-qty" title="增加數量">
                                                    <i class="fa-solid fa-plus"></i>
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                    <td style="text-align: right; font-weight: 700;">
                                        NT$ <fmt:formatNumber value="${item.subtotal}" pattern="#,###" />
                                    </td>
                                    <td style="text-align: center;">
                                        <form action="${pageContext.request.contextPath}/cart/remove" method="POST" style="margin: 0;">
                                            <input type="hidden" name="productId" value="${item.product.id}" />
                                            <button type="submit" class="btn-remove" title="移除此商品">
                                                <i class="fa-solid fa-trash"></i>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="total-section">
                        <span class="total-label">總計金額：</span>
                        <!-- 你之前已經成功將這裡替換成 .totalPrice 了！ -->
                        <span class="total-amount">NT$ <fmt:formatNumber value="${sessionScope.cart.totalPrice}" pattern="#,###" /></span>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="actions">
                <a href="${pageContext.request.contextPath}/products" class="btn-secondary">
                    <i class="fa-solid fa-arrow-left"></i> 繼續挑選服務
                </a>
                <c:if test="${not empty sessionScope.cart.items}">
                    <a href="javascript:alert('感謝體驗！本系統為 SSR 技術架構展示平台。');" class="btn-checkout">
                        <i class="fa-solid fa-credit-card"></i> 前往結帳
                    </a>
                </c:if>
            </div>
        </div>
    </main>

</body>
</html>