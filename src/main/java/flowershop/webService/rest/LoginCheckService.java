package flowershop.webService.rest;

import flowershop.backend.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/check_login")
@Service
public class LoginCheckService {
    @Autowired
    private UserService userService;

    @GET
    @Path("/{login}")
    public boolean checkLogin(@PathParam("login") String login) {
        LoggerFactory.getLogger(LoginCheckService.class).info("login {} checked", login);

        return userService.find(login) != null;
    }
}
