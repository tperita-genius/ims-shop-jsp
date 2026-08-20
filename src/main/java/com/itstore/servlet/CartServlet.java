package com.itstore.servlet;

import com.itstore.dao.ProductDao;
import com.itstore.model.CartItem;
import com.itstore.model.ServiceProduct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<CartItem> cart = getOrCreateCart(session);

        long totalAmount = cart.stream().mapToLong(CartItem::getSubtotal).sum();
        req.setAttribute("cartItems", cart);
        req.setAttribute("totalAmount", totalAmount);

        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String productId = req.getParameter("productId");
        HttpSession session = req.getSession();
        List<CartItem> cart = getOrCreateCart(session);

        if ("add".equals(action) && productId != null) {
            String requirement = req.getParameter("requirement");
            ServiceProduct product = productDao.getProductById(productId);

            if (product != null) {
                boolean found = false;
                for (CartItem item : cart) {
                    if (item.getProduct().getId().equals(productId)) {
                        item.setQuantity(item.getQuantity() + 1);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    cart.add(new CartItem(product, 1, requirement != null ? requirement : ""));
                }
            }
        } else if ("remove".equals(action)) {
            cart.removeIf(item -> item.getProduct().getId().equals(productId));
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getOrCreateCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}