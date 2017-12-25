package servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import customInjections.logger.InjectLogger;
import dao.AccountingDao;
import domain.Accounting;
import org.apache.logging.log4j.core.Logger;
import service.MyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ActivityServlet extends HttpServlet {
    @Inject
    private Gson gson;
    @Inject
    private AccountingDao accountingDao;
    @InjectLogger
    private
    Logger logger;

    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Start ActivityServlet");
        PrintWriter out = resp.getWriter();
        try {
            if (req.getParameter("id") != null) {
                out.println(idActivityToJson(req, accountingDao));
            } else if (req.getParameter("authorityId") != null) {
                out.println(authorityIdActivityToJson(req, accountingDao));
            } else {
                out.println(allActivitiesToJson(accountingDao));
            }
        } catch (MyException e) {
            logger.error("Database error");
        }
        logger.debug("End of work ActivityServlet");
    }

    private String allActivitiesToJson(AccountingDao accountingDao) throws IOException, MyException {
        ArrayList<Accounting> accountings = accountingDao.findAllActivities();
        if (accountings != null) {
            return gson.toJson(accountings);
        }
        return gson.toJson("Activities not found");
    }

    private String idActivityToJson(HttpServletRequest req,
                                    AccountingDao accountingDao) throws IOException, MyException {
        Accounting accounting = accountingDao.findActivityById(Long.parseLong(req.getParameter("id")));
        if (accounting != null) {
            return gson.toJson(accounting);
        }
        return gson.toJson("Activity with id: " + req.getParameter("id") + " not found");
    }

    private String authorityIdActivityToJson(HttpServletRequest req,
                                             AccountingDao accountingDao) throws IOException, MyException {
        Accounting accounting = accountingDao.
                findActivityByAuthorityId(Long.parseLong(req.getParameter("authorityId")));
        if (accounting != null) {
            return gson.toJson(accounting);
        }
        return gson.toJson("Activity with authorityId: "
                + req.getParameter("authorityId") + " not found");
    }
}