package flowershop.frontend.servlets;

import flowershop.frontend.dto.Flower;
import flowershop.frontend.dto.User;
import flowershop.backend.enums.SessionAttribute;
import flowershop.backend.enums.Path;
import flowershop.backend.services.FlowerService;
import flowershop.backend.exception.FlowerValidationException;
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
import java.math.BigDecimal;
import java.util.Map;

@WebServlet(urlPatterns = {"/", "/flower/*"})
public class FlowerControllerServlet extends HttpServlet {
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
            case FLOWER_NEW:
            case FLOWER_UPDATE:
                if (isAccessGranted(req)) {
                    req.setAttribute("action", path.getValue());
                    if (path == Path.FLOWER_UPDATE)
                        req.setAttribute("flower", flowerService.find(getIdParam(req)));
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            case FLOWER_INDEX:
                req.setAttribute("flowers", flowerService.getAll());
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
            case FLOWER_NEW:
            case FLOWER_UPDATE:
                if (isAccessGranted(req)) {
                    try {
                        if (path == Path.FLOWER_NEW)
                            flowerService.create(parseFlower(req));
                        else
                            flowerService.update(parseFlower(req));
                    } catch (FlowerValidationException e) {
                        handleValidationError(e, req);

                        req.setAttribute("action", path.getValue());

                        req.getRequestDispatcher(path.getPage()).forward(req, resp);
                        return;
                    }
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            case FLOWER_DELETE:
                if (isAccessGranted(req)) {
                    Long id = getIdParam(req);
                    flowerService.delete(flowerService.find(id));
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
                return;
        }
        resp.sendRedirect(Path.FLOWER_INDEX.getValue());
    }

    private void handleValidationError(FlowerValidationException e, HttpServletRequest req) {
        String attrName;
        switch (e.getMessage()) {
            case FlowerValidationException.NEGATIVE_COUNT:
            case FlowerValidationException.WRONG_COUNT:
                attrName = "countErrorMsg";
                break;
            case FlowerValidationException.NEGATIVE_PRICE:
            case FlowerValidationException.WRONG_PRICE:
                attrName = "priceErrorMsg";
                break;
            default:
                attrName = "anotherErrorMsg";
        }
        req.setAttribute(attrName, e.getMessage());

        // Copying parameters to fill the form
        for (Map.Entry<String, String[]> p : req.getParameterMap().entrySet()) {
            req.setAttribute(p.getKey(), p.getValue()[0]);
        }
    }

    public Flower parseFlower(HttpServletRequest req) throws FlowerValidationException {
        // parse id
        String parameter = req.getParameter("id");
        Long id = (parameter == null || parameter.equals("")) ? null : Long.parseLong(parameter);

        // parse name
        String name = req.getParameter("name");

        // parse price
        BigDecimal price;
        try {
            price = new BigDecimal(req.getParameter("price"));
        } catch (NumberFormatException e) {
            throw new FlowerValidationException(FlowerValidationException.WRONG_PRICE);
        }

        // parse count
        parameter = req.getParameter("count");
        if (parameter.equals(""))
            parameter = "0";
        Integer count;
        try {
            count = Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            throw new FlowerValidationException(FlowerValidationException.WRONG_COUNT);
        }

        return new Flower(id, name, price, count);
    }



    private boolean isAccessGranted(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute(SessionAttribute.USER.getValue());
        return (user != null && user.isAdmin());
    }

    private Long getIdParam(HttpServletRequest req) {
        return Long.parseLong(req.getPathInfo().split("/")[2]);
    }

    private void refreshSessionUser(HttpServletRequest req) {
        User sessionUser = (User) req.getSession().getAttribute(SessionAttribute.USER.getValue());
        sessionUser = sessionUser == null ? null : userService.find(sessionUser.getLogin());
        req.getSession().setAttribute(SessionAttribute.USER.getValue(), sessionUser);
    }
}
