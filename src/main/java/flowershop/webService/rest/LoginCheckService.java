package flowershop.webService.rest;

import flowershop.backend.entity.UserEntity;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/check_login")
@Service
public class LoginCheckService {
    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("/{login}")
    public boolean checkLogin(@PathParam("login") String login) {
        LoggerFactory.getLogger(LoginCheckService.class).info("login " + login + " checked");

        return em.find(UserEntity.class, login) != null;
    }
}
