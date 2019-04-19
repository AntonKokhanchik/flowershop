package flowershop.frontend.servlets;

import flowershop.backend.dto.User;
import flowershop.backend.enums.Path;
import flowershop.backend.services.UserService;
import flowershop.backend.exception.UserValidationException;
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

@WebServlet(urlPatterns = "/user/*")
public class UserControllerServlet extends HttpServlet {
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

        if (path == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
            return;
        }

        switch (path){
            case USER_INDEX:
                if (userService.isAccessGranted(req)) {
                    req.setAttribute("users", userService.getAll());
                    break;
                }
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;

            case LOGIN:
            case REGISTER:
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
            case LOGOUT:
                req.getSession().removeAttribute("sessionUser");
                resp.sendRedirect(req.getHeader("referer"));
                return;

            case REGISTER:
                try {
                    userService.create(userService.parse(req));
                } catch (UserValidationException e){
                    handleValidationError(e, req);

                    req.getRequestDispatcher(path.getPage()).forward(req, resp);
                    return;
                }

                resp.sendRedirect(Path.LOGIN.getPath());
                return;

            case LOGIN:
                User user;
                try {
                    user = userService.verify(req);
                }catch(UserValidationException e){
                    handleValidationError(e, req);

                    req.getRequestDispatcher(path.getPage()).forward(req, resp);
                    return;
                }

                req.getSession().setAttribute("sessionUser", user);
                resp.sendRedirect(Path.FLOWER_INDEX.getPath());
                return;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
                return;
        }
    }

    private void handleValidationError(UserValidationException e, HttpServletRequest req){
        String attrName = "anotherErrorMsg";
        switch (e.getMessage()) {
            case UserValidationException.LOGIN_IS_TAKEN:
            case UserValidationException.WRONG_LOGIN:
                attrName = "loginErrorMsg";
                break;
            case UserValidationException.WRONG_PASSWORD:
                attrName = "passwordErrorMsg";
                break;
        }
        req.setAttribute(attrName, e.getMessage());

        // Copying parameters to fill the form
        for (Map.Entry<String, String[]> p : req.getParameterMap().entrySet()) {
            req.setAttribute(p.getKey(), p.getValue()[0]);
        }
    }
}
