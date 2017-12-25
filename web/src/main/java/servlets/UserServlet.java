package servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import customInjections.logger.InjectLogger;
import dao.AuthenticationDao;
import domain.User;
import org.apache.logging.log4j.core.Logger;
import service.MyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UserServlet extends HttpServlet {
    @Inject
    private Gson gson;
    @Inject
    private AuthenticationDao authenticationDao;
    @InjectLogger
    private
    Logger logger;

    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Start UserServlet");
        PrintWriter out = resp.getWriter();
        try {
            if (req.getParameter("id") == null) {
                out.println(allUsersToJson(authenticationDao));
            } else {
                out.println(idUserToJson(req, authenticationDao));
            }
        } catch (MyException e) {
            logger.error("Database error");
        }
        logger.debug("End of work UserServlet");
    }

    private String allUsersToJson(AuthenticationDao authenticationDao) throws MyException, IOException {
        ArrayList<User> users = authenticationDao.findAllUsers();
        if (users != null) {
            return gson.toJson(users);
        }
        return gson.toJson("Users not found");
    }

    private String idUserToJson(HttpServletRequest req,
                                AuthenticationDao authenticationDao) throws IOException, MyException {
        User user = authenticationDao.findUserById(Long.parseLong(req.getParameter("id")));
        if (user != null) {
            return gson.toJson(user);
        }
        return gson.toJson("User with id: " + req.getParameter("id") + " not found");
    }
}