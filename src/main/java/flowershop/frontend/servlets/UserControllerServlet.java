package flowershop.frontend.servlets;

import flowershop.backend.dto.User;
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
                req.setAttribute("users", userService.getAll());
                page = "/userIndex.jsp";
                break;

            case "login":
                page = "/login.jsp";
                break;

            case "register":
                page = "/register.jsp";
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
            case "logout":
                req.getSession().removeAttribute("sessionUser");
                resp.sendRedirect(req.getHeader("referer"));
                return;

            case "register":
                try {
                    userService.create(userService.parse(req));
                } catch (UserValidationException e){
                    handleValidationError(e, req);

                    req.getRequestDispatcher("/register.jsp").forward(req, resp);
                    return;
                }

                resp.sendRedirect("/user/login");
                return;

            case "login":
                User user;
                try {
                    user = userService.verify(req);
                }catch(UserValidationException e){
                    handleValidationError(e, req);

                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                    return;
                }

                req.getSession().setAttribute("sessionUser", user);
                resp.sendRedirect("/flower");
                return;

            case "error":
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
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
