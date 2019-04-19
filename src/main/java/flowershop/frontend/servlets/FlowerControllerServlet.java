package flowershop.frontend.servlets;

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
        Path path = Path.get(req.getRequestURI());

        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
            return;
        }

        switch (path){
            case FLOWER_NEW:
            case FLOWER_UPDATE:
                if (userService.isAccessGranted(req)) {
                    req.setAttribute("action", path.getPath());
                    if (path == Path.FLOWER_UPDATE)
                        req.setAttribute("flower", flowerService.find(getId(req)));
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
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = Path.get(req.getRequestURI());

        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
            return;
        }

        switch (path){
            case FLOWER_NEW:
            case FLOWER_UPDATE:
                if (userService.isAccessGranted(req)) {
                    try {
                        if (path == Path.FLOWER_NEW)
                            flowerService.create(flowerService.parse(req));
                        else
                            flowerService.update(flowerService.parse(req));
                    } catch (FlowerValidationException e) {
                        handleValidationError(e, req);

                        req.setAttribute("action", path.getPath());

                        req.getRequestDispatcher(path.getPage()).forward(req, resp);
                        return;
                    }
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            case FLOWER_DELETE:
                if (userService.isAccessGranted(req)) {
                    Long id = getId(req);
                    flowerService.delete(flowerService.find(id));
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
                return;
        }
        resp.sendRedirect(Path.FLOWER_INDEX.getPath());
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

    private Long getId(HttpServletRequest req){
        return Long.parseLong(req.getPathInfo().split("/")[2]);
    }
}
