<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>購物車清單 - Tailwind SSR</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 text-slate-800 antialiased min-h-screen flex flex-col justify-between">

  <!-- 頂部導覽列 -->
  <nav class="bg-slate-900 border-b border-slate-800 sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <a href="${pageContext.request.contextPath}/" class="text-white font-extrabold text-xl tracking-tight flex items-center space-x-2">
          <span>🚀 IT Core Service</span>
        </a>
        <a href="${pageContext.request.contextPath}/" class="text-slate-300 hover:text-white text-sm font-medium transition-colors">
          ← 繼續選購商品
        </a>
      </div>
    </div>
  </nav>

  <main class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-10 flex-grow w-full">
    <h2 class="text-2xl font-bold text-slate-900 mb-6">🛒 您的預約購物車</h2>

    <c:choose>
      <c:when test="${empty cartItems}">
        <div class="bg-white rounded-2xl border border-slate-200 p-12 text-center shadow-sm">
          <p class="text-slate-500 text-lg mb-6">購物車目前沒有任何服務項目</p>
          <a href="${pageContext.request.contextPath}/" class="inline-flex bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-6 py-2.5 rounded-xl transition-all shadow">
            前往瀏覽服務
          </a>
        </div>
      </c:when>
      <c:otherwise>
        <div class="bg-white rounded-2xl border border-slate-200 overflow-hidden shadow-sm mb-6">
          <div class="divide-y divide-slate-100">
            <c:forEach var="item" items="${cartItems}">
              <div class="p-6 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
                <div class="flex-grow">
                  <h4 class="font-bold text-slate-900 text-lg mb-1">${item.product.title}</h4>
                  <p class="text-xs text-slate-400 font-mono">ID: ${item.product.id}</p>
                </div>

                <div class="flex items-center justify-between sm:justify-end gap-6 w-full sm:w-auto">
                  <div class="text-right">
                    <span class="text-xs text-slate-400 block">數量: ${item.quantity}</span>
                    <span class="font-bold text-slate-900 text-lg">
                      NT$ <fmt:formatNumber value="${item.subtotal}" type="number"/>
                    </span>
                  </div>

                  <form action="${pageContext.request.contextPath}/cart" method="post">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="productId" value="${item.product.id}">
                    <button type="submit" class="text-red-500 hover:text-red-700 bg-red-50 hover:bg-red-100 px-3 py-1.5 rounded-lg text-xs font-semibold transition-colors">
                      移除
                    </button>
                  </form>
                </div>
              </div>
            </c:forEach>
          </div>
        </div>

        <div class="bg-white rounded-2xl border border-slate-200 p-6 flex flex-col sm:flex-row justify-between items-center gap-4 shadow-sm">
          <div>
            <span class="text-slate-500 text-sm">訂單總金額</span>
            <div class="text-3xl font-extrabold text-indigo-600">
              <span class="text-sm font-normal text-slate-500 mr-1">NT$</span>
              <fmt:formatNumber value="${totalAmount}" type="number"/>
            </div>
          </div>
          <button onclick="alert('訂單已提交！')" class="w-full sm:w-auto bg-indigo-600 hover:bg-indigo-500 text-white font-bold px-8 py-3 rounded-xl transition-all shadow-lg hover:shadow-indigo-500/25">
            確認送出預約
          </button>
        </div>
      </c:otherwise>
    </c:choose>
  </main>

  <footer class="bg-white border-t border-slate-200 mt-16 py-6 text-center text-xs text-slate-400">
    © 2026 IT Service Shop. All rights reserved.
  </footer>

</body>
</html>