package flowershop.frontend.servlets;

import flowershop.backend.services.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/flower/*")
public class FlowerControllerServlet extends HttpServlet {
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

        String page = "";
        switch (action){
            case "new":
                page = "/flowerNew.jsp";
                break;

            case "update":
                Long updateId = Long.parseLong(path.split("/")[2]);
                req.setAttribute("flower", flowerService.find(updateId));
                page = "/flowerUpdate.jsp";
                break;

            case "index":
                req.setAttribute("flowers", flowerService.getAll());
                page = "/flowerIndex.jsp";
                break;
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        String action = path.split("/")[1];

        switch (action){
            case "new":
                flowerService.create(flowerService.parse(req));
                break;

            case "update":
                flowerService.update(flowerService.parse(req));
                break;

            case "delete":
                Long id = Long.parseLong(path.split("/")[2]);
                flowerService.delete(flowerService.find(id));
                break;
        }
        resp.sendRedirect("/flower");
    }
}
