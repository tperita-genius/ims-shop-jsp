<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>IT 專業服務商城 - Tailwind SSR</title>
  <!-- 引入 Tailwind CSS CDN -->
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 text-slate-800 antialiased min-h-screen flex flex-col justify-between">

  <!-- 頂部導覽列 -->
  <nav class="bg-slate-900 border-b border-slate-800 sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <a href="${pageContext.request.contextPath}/" class="flex items-center space-x-3 text-white font-extrabold text-xl tracking-tight">
          <span class="p-2 bg-indigo-600 rounded-lg text-white">🚀</span>
          <span>IT Core Service</span>
          <span class="text-xs px-2 py-0.5 bg-indigo-900/60 border border-indigo-500/30 text-indigo-300 rounded-full">SSR</span>
        </a>
        <a href="${pageContext.request.contextPath}/cart" class="inline-flex items-center space-x-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-semibold px-4 py-2 rounded-lg transition-all shadow-md hover:shadow-indigo-500/20">
          <span>🛒 查看購物車</span>
        </a>
      </div>
    </div>
  </nav>

  <!-- 主內容區 -->
  <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10 flex-grow w-full">
    <!-- Hero 標題 -->
    <div class="text-center max-w-2xl mx-auto mb-12">
      <h1 class="text-3xl sm:text-4xl font-extrabold text-slate-900 tracking-tight mb-3">
        企業級 IT 解決方案
      </h1>
      <p class="text-slate-500 text-base">
        即時同步 Supabase 雲端資料庫與 Tomcat 伺服器端渲染 (SSR) 產生
      </p>
    </div>

    <!-- 商品卡片列表 -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
      <c:forEach var="p" items="${products}">
        <div class="bg-white rounded-2xl p-6 border border-slate-200/80 shadow-sm hover:shadow-xl transition-all duration-300 flex flex-col justify-between group">
          <div>
            <div class="flex justify-between items-center mb-4">
              <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold bg-emerald-100 text-emerald-800">
                ● 服務提供中
              </span>
              <span class="text-xs text-slate-400 font-mono">
                ID: ${p.id.length() > 8 ? p.id.substring(0, 8) : p.id}...
              </span>
            </div>

            <h3 class="text-xl font-bold text-slate-900 group-hover:text-indigo-600 transition-colors mb-2">
              ${p.title}
            </h3>

            <p class="text-slate-600 text-sm leading-relaxed mb-6">
              ${p.description}
            </p>
          </div>

          <div class="pt-4 border-t border-slate-100">
            <div class="flex justify-between items-baseline mb-4">
              <span class="text-xs text-slate-400 font-medium">預估費用</span>
              <div class="text-2xl font-black text-slate-900">
                <span class="text-xs font-normal text-slate-500 mr-1">NT$</span>
                <fmt:formatNumber value="${p.price}" type="number"/>
              </div>
            </div>

            <form action="${pageContext.request.contextPath}/cart" method="post">
              <input type="hidden" name="action" value="add">
              <input type="hidden" name="productId" value="${p.id}">
              <button type="submit" class="w-full bg-slate-900 hover:bg-indigo-600 text-white font-medium py-2.5 px-4 rounded-xl transition-all duration-200 shadow-sm hover:shadow">
                + 加入購物車
              </button>
            </form>
          </div>
        </div>
      </c:forEach>
    </div>
  </main>

  <!-- 頁尾 -->
  <footer class="bg-white border-t border-slate-200 mt-16 py-6 text-center text-xs text-slate-400">
    © 2026 IT Service Shop. All rights reserved. Server-Side Rendered by Apache Tomcat 9.
  </footer>

</body>
</html>