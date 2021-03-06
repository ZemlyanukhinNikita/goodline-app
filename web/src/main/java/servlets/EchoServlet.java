package servlets;

import customInjections.logger.InjectLogger;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EchoServlet extends HttpServlet {
    @InjectLogger
    private
    Logger logger;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        logger.debug("Start EchoServlet method doGet");
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("get?id=" + req.getParameter("id"));
        logger.debug("Start EchoServlet method doPost");
    }
}
