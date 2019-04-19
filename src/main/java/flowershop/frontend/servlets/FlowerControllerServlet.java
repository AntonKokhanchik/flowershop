package flowershop.frontend.servlets;

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
import java.util.Map;

@WebServlet(urlPatterns = "/flower/*")
public class FlowerControllerServlet extends HttpServlet {
    @Autowired
    private FlowerService flowerService;

    @Autowired
    private UserService userService;

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
            case "new":
                if (userService.isAccessGranted(req)) {
                    req.setAttribute("action", action);
                    page = "/flowerForm.jsp";
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            case "update":
                if (userService.isAccessGranted(req)) {
                    req.setAttribute("action", action);
                    Long id = Long.parseLong(path.split("/")[2]);
                    req.setAttribute("flower", flowerService.find(id));
                    page = "/flowerForm.jsp";
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            case "index":
                req.setAttribute("flowers", flowerService.getAll());
                page = "/flowerIndex.jsp";
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
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
            case "new":
            case "update":
                if (userService.isAccessGranted(req)) {
                    try {
                        if (action == "new")
                            flowerService.create(flowerService.parse(req));
                        else
                            flowerService.update(flowerService.parse(req));
                    } catch (FlowerValidationException e) {
                        handleValidationError(e, req);

                        req.setAttribute("action", action);

                        req.getRequestDispatcher("/flowerForm.jsp").forward(req, resp);
                        return;
                    }
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            case "delete":
                if (userService.isAccessGranted(req)) {
                    Long id = Long.parseLong(path.split("/")[2]);
                    flowerService.delete(flowerService.find(id));
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            case "error":
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
                return;
        }
        resp.sendRedirect("/flower");
    }

    private void handleValidationError(FlowerValidationException e, HttpServletRequest req) {
        String attrName = "anotherErrorMsg";
        switch (e.getMessage()) {
            case FlowerValidationException.NEGATIVE_COUNT:
            case FlowerValidationException.WRONG_COUNT:
                attrName = "countErrorMsg";
                break;
            case FlowerValidationException.NEGATIVE_PRICE:
            case FlowerValidationException.WRONG_PRICE:
                attrName = "priceErrorMsg";
                break;
        }
        req.setAttribute(attrName, e.getMessage());

        // Copying parameters to fill the form
        for (Map.Entry<String, String[]> p : req.getParameterMap().entrySet()) {
            req.setAttribute(p.getKey(), p.getValue()[0]);
        }
    }
}
