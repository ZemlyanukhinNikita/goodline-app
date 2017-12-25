package servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import customInjections.logger.InjectLogger;
import dao.AuthorizationDao;
import domain.ResourceUsersRoles;
import org.apache.logging.log4j.core.Logger;
import service.MyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AuthorityServlet extends HttpServlet {
    @Inject
    private Gson gson;
    @Inject
    private AuthorizationDao authorizationDao;
    @InjectLogger
    private
    Logger logger;

    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Start AuthorityServlet");
        PrintWriter out = resp.getWriter();
        try {
            if (req.getParameter("id") != null) {
                out.println(idRoleToJson(req, authorizationDao));
            } else if (req.getParameter("userId") != null) {
                out.println(userIdRoleToJson(req, authorizationDao));
            } else {
                out.println(rolesToJson(authorizationDao));
            }
        } catch (MyException e) {
            logger.error("Database error");
        }
        logger.debug("End of work AuthorityServlet");
    }

    private String rolesToJson(AuthorizationDao authorizationDao) throws IOException, MyException {
        ArrayList<ResourceUsersRoles> resourceUsersRoles = authorizationDao.findRoles();
        if (resourceUsersRoles != null) {
            return gson.toJson(resourceUsersRoles);
        }
        return gson.toJson("Roles not found");
    }

    private String idRoleToJson(HttpServletRequest req,
                                AuthorizationDao authorizationDao) throws IOException, MyException {
        ResourceUsersRoles resourceUsersRoles = authorizationDao.
                findRolesById(Long.parseLong(req.getParameter("id")));
        if (resourceUsersRoles != null) {
            return gson.toJson(resourceUsersRoles);
        }
        return gson.toJson("Role with id: " + req.getParameter("id") + " not found");
    }

    private String userIdRoleToJson(HttpServletRequest req,
                                    AuthorizationDao authorizationDao) throws IOException, MyException {
        ArrayList<ResourceUsersRoles> resourceUsersRoles = authorizationDao.
                findRolesByUserId(Long.parseLong(req.getParameter("userId")));
        return gson.toJson(resourceUsersRoles);
    }
}
