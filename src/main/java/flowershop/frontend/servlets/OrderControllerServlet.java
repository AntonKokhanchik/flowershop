package flowershop.frontend.servlets;

import flowershop.backend.dto.User;
import flowershop.backend.services.Cart;
import flowershop.backend.services.FlowerService;
import flowershop.backend.exception.FlowerValidationException;
import flowershop.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/order/*")
public class OrderControllerServlet extends HttpServlet {
    @Autowired
    private OrderService orderService;
    @Autowired
    private FlowerService flowerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        String action;

        if (path == null || path.split("/").length < 2)
            action = "index";
        else
            action = path.split("/")[1];

        String page;
        switch (action){
            case "index":
                User user = (User)req.getSession().getAttribute("sessionUser");

                if (user != null)
                    if (user.isAdmin())
                        req.setAttribute("orders", orderService.getAll());
                    else
                        req.setAttribute("orders", orderService.getByUser(user));

                page = "/orderIndex.jsp";
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        String action;

        if (path == null || path.split("/").length < 2)
            action = "error";
        else
            action = path.split("/")[1];

        switch (action){
            case "new": {
                Cart cart = (Cart) req.getSession().getAttribute("sessionCart");

                try {
                    orderService.create(cart, (User) req.getSession().getAttribute("sessionUser"));
                }catch(FlowerValidationException e) {
                    req.getSession().setAttribute("cartErrorMsg", e.getMessage());

                }
                cart.clear();
                req.getSession().setAttribute("sessionCart", cart);

                resp.sendRedirect("/order");
                return;
            }

            case "add_to_cart": {
                Cart cart = (Cart) req.getSession().getAttribute("sessionCart");
                if (cart == null)
                    cart = new Cart();
                Long flowerId = Long.parseLong(path.split("/")[2]);

                cart.addItem(flowerService.find(flowerId));

                req.getSession().setAttribute("sessionCart", cart);

                resp.sendRedirect("/flower");
                return;
            }

            case "remove_from_cart": {
                Cart cart = (Cart) req.getSession().getAttribute("sessionCart");
                Long flowerId = Long.parseLong(path.split("/")[2]);

                cart.removeItem(flowerService.find(flowerId));

                req.getSession().setAttribute("sessionCart", cart);
                resp.sendRedirect("/flower");
                return;
            }

            case "delete":
                Long id = Long.parseLong(path.split("/")[2]);
                resp.sendRedirect("/order");
                return;

            case "error":
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }

    }
}