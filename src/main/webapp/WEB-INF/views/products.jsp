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
            <a href="${pageContext.request.contextPath}/cart" class="btn-cart" id="btnCartHeader">
                <i class="fa-solid fa-cart-shopping"></i> 查看購物車
                <span class="cart-badge-count" id="cartBadgeCount" style="${empty sessionScope.cart ? 'display:none;' : ''}">
                    <c:set var="initialCount" value="0" />
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <c:set var="initialCount" value="${initialCount + item.quantity}" />
                    </c:forEach>
                    ${initialCount}
                </span>
            </a>
        </div>
    </header>

    <!-- 右上角加入成功的小彈窗（位於查看購物車按鈕正下方，不遮擋按鈕） -->
    <div id="miniCartPopup" class="mini-cart-popup">
        <div class="mini-cart-header">
            <span><i class="fa-solid fa-circle-check" style="color: #16a34a; margin-right: 6px;"></i>已加入購物車</span>
            <button type="button" class="mini-cart-close" onclick="closeMiniCart()"><i class="fa-solid fa-xmark"></i></button>
        </div>
        <div class="mini-cart-list" id="miniCartList">
            <!-- 動態由 JavaScript 填入 -->
        </div>
        <div class="mini-cart-footer">
            <div class="mini-cart-total">
                <span>合計金額</span>
                <span id="miniCartTotal">NT$ 0</span>
            </div>
            <a href="${pageContext.request.contextPath}/cart" class="mini-cart-btn">
                前往購物車結帳 <i class="fa-solid fa-arrow-right"></i>
            </a>
        </div>
    </div>

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
                        <button type="button" class="btn-add" onclick="handleAddToCart('${product.id}')">
                            <i class="fa-solid fa-plus"></i> 加入
                        </button>
                    </div>
                </div>
            </c:forEach>
        </div>
    </main>

    <script>
        let autoCloseTimer = null;

        function handleAddToCart(productId) {
            const formData = new URLSearchParams();
            formData.append('productId', productId);

            fetch('${pageContext.request.contextPath}/api/cart/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                body: formData
            })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    renderMiniCart(data);
                } else {
                    alert(data.message || '加入失敗');
                }
            })
            .catch(err => {
                console.error('Add to cart error:', err);
            });
        }

        function renderMiniCart(data) {
            const popup = document.getElementById('miniCartPopup');
            const list = document.getElementById('miniCartList');
            const total = document.getElementById('miniCartTotal');
            const badge = document.getElementById('cartBadgeCount');

            // 1. 更新按鈕上的數量角標
            badge.innerText = data.totalQuantity;
            badge.style.display = data.totalQuantity > 0 ? 'inline-block' : 'none';

            // 2. 渲染彈窗清單
            list.innerHTML = '';
            data.cart.forEach(item => {
                const itemDiv = document.createElement('div');
                itemDiv.className = 'mini-cart-item';
                itemDiv.innerHTML = `
                    <div>
                        <div class="mini-cart-item-title">\${item.product.title}</div>
                        <div class="mini-cart-item-meta">數量: \${item.quantity}</div>
                    </div>
                    <div style="font-weight: 700; color: #0f172a;">NT$ \${item.subtotal.toLocaleString()}</div>
                `;
                list.appendChild(itemDiv);
            });

            // 3. 更新合計金額
            total.innerText = 'NT$ ' + data.totalPrice.toLocaleString();

            // 4. 顯示彈窗
            popup.style.display = 'block';

            // 5. 5 秒後自動收起，若再次點選則重置計時
            if (autoCloseTimer) clearTimeout(autoCloseTimer);
            autoCloseTimer = setTimeout(() => {
                closeMiniCart();
            }, 5000);
        }

        function closeMiniCart() {
            const popup = document.getElementById('miniCartPopup');
            if (popup) {
                popup.style.display = 'none';
            }
        }
    </script>

</body>
</html>