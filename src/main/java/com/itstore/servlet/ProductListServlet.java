package com.itstore.servlet;

import com.itstore.dao.ProductDao;
import com.itstore.model.ServiceProduct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"", "/products"})
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<ServiceProduct> products = productDao.getAllProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
    }
}