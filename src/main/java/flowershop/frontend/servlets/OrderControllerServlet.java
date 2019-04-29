package flowershop.frontend.servlets;

import flowershop.backend.dto.Order;
import flowershop.backend.dto.User;
import flowershop.backend.enums.Path;
import flowershop.backend.exception.FlowerValidationException;
import flowershop.backend.services.Cart;
import flowershop.backend.services.FlowerService;
import flowershop.backend.services.OrderService;
import flowershop.backend.services.UserService;
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
    @Autowired
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        config.getServletContext().setAttribute("path", Path.getPathMap());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        refreshSessionUser(req);

        Path path = Path.get(req.getRequestURI());

        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
            return;
        }

        switch (path) {
            case ORDER_INDEX:
                User user = (User) req.getSession().getAttribute("sessionUser");

                if (user != null)
                    if (user.isAdmin())
                        req.setAttribute("orders", orderService.getAll());
                    else
                        req.setAttribute("orders", orderService.getByUser(user));

                break;

            case ORDER_SHOW:
                Order order = orderService.find(getIdParam(req));
                req.setAttribute("order", order);
                req.setAttribute("items", orderService.getFlowersData(order));
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
                return;
        }
        req.getRequestDispatcher(path.getPage()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = Path.get(req.getRequestURI());

        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
            return;
        }

        switch (path) {
            case ORDER_NEW: {
                Cart cart = (Cart) req.getSession().getAttribute("sessionCart");

                try {
                    orderService.create(cart, (User) req.getSession().getAttribute("sessionUser"));
                } catch (FlowerValidationException e) {
                    req.getSession().setAttribute("cartErrorMsg", e.getMessage());
                }
                cart.clear();
                req.getSession().setAttribute("sessionCart", cart);
                req.getSession().setAttribute("sessionDetailedCart", orderService.generateDetailedCart(cart));

                resp.sendRedirect(Path.ORDER_INDEX.getPath());
                return;
            }

            case ADD_TO_CART: {
                Cart cart = (Cart) req.getSession().getAttribute("sessionCart");
                if (cart == null)
                    cart = new Cart();
                Long flowerId = getIdParam(req);

                cart.addItem(flowerService.find(flowerId));

                req.getSession().setAttribute("sessionCart", cart);
                req.getSession().setAttribute("sessionDetailedCart", orderService.generateDetailedCart(cart));

                resp.sendRedirect(Path.FLOWER_INDEX.getPath());
                return;
            }

            case REMOVE_FROM_CART: {
                Cart cart = (Cart) req.getSession().getAttribute("sessionCart");
                Long id = getIdParam(req);

                cart.removeItem(flowerService.find(id));

                req.getSession().setAttribute("sessionCart", cart);
                req.getSession().setAttribute("sessionDetailedCart", orderService.generateDetailedCart(cart));
                resp.sendRedirect(Path.FLOWER_INDEX.getPath());
                return;
            }

            case ORDER_PAY: {
                User user = (User) req.getSession().getAttribute("sessionUser");
                Order order = orderService.find(getIdParam(req));

                if (user.getLogin().equals(order.getOwner().getLogin()))
                    if (user.getBalance().compareTo(order.getFullPrice()) >= 0) {
                        orderService.pay(order);
                        req.getSession().setAttribute("sessionUser", userService.find(user.getLogin()));
                    }

                resp.sendRedirect(Path.ORDER_INDEX.getPath());
                return;
            }

            case ORDER_CLOSE: {
                if (isAccessGranted(req)) {
                    Order order = orderService.find(getIdParam(req));
                    orderService.close(order);

                    resp.sendRedirect(Path.ORDER_INDEX.getPath());
                    return;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            case ORDER_DELETE:
                if (isAccessGranted(req)) {
                    Long id = getIdParam(req);
                    orderService.delete(orderService.find(id));

                    resp.sendRedirect(Path.ORDER_INDEX.getPath());
                    return;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
                return;
        }
    }

    public boolean isAccessGranted(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("sessionUser");
        return (user != null && user.isAdmin());
    }

    private Long getIdParam(HttpServletRequest req){
        return Long.parseLong(req.getPathInfo().split("/")[2]);
    }

    public void refreshSessionUser(HttpServletRequest req){
        User sessionUser = (User) req.getSession().getAttribute("sessionUser");
        sessionUser = sessionUser == null ? null : userService.find(sessionUser.getLogin());
        req.getSession().setAttribute("sessionUser", sessionUser);
    }
}
