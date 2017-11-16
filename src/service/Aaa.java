package service;

import dao.AaaDao;
import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.Roles;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

public class Aaa {
    private static final Logger logger = LogManager.getLogger(Aaa.class.getName());

    private static User getUser(String login) throws SQLException {
        return AaaDao.getDataFromTableUser(login);
    }
    public static int authenticate(String login, String pass) throws SQLException {
        User user= getUser(login);
        if(AaaDao.getDataFromTableUser(login)==null) {
            logger.error("login " + login + " not found in the database");
            return 1;
        }
        if (AaaDao.getDataFromTableUser(login) != null && !Hash.isRightHashPassword(pass,user.getPassword(),user.getSalt())) {
            logger.error("password " + pass + " for User " + login + "not found in the database");
            return 2;
        }
        return 0;
    }


    public static int authorize(String login, String role, String resource) throws SQLException {

        User user = getUser(login);
        ArrayList<ResourceUsersRoles> resourceUsersRoles1 = AaaDao.getDataFromTableResourceUsersRoles(user);
        if (!Roles.isValidRole(role)) {
            logger.error("role " + role + " invalid");
            return 3;
        }

        if (resourceUsersRoles1 != null) {
            for (ResourceUsersRoles resUserRole : resourceUsersRoles1) {
                if (role.equals(resUserRole.getRole().toString())
                        && Validation.isCorrectPath(resource, resUserRole.getPath()))
                    return 0;
            }
        }
        logger.error("resource " + resource + " not found by User " + login);
        return 4;
    }

    public static int account(String dateStart, String dateEnd, String volume,
                              ArrayList<Accounting> accounting) throws SQLException {
        //Проверка валидности объема и дат.
        if ((!Validation.isValidVolume(volume)) || (!Validation.isValidDate(dateStart))
                || (!Validation.isValidDate(dateEnd))) {
            logger.error("Invalid dates or volume");
            return 5;
        } else {
            accounting.add(new Accounting(dateStart,dateEnd,volume));
            AaaDao.setDataToTableAccounting(new Accounting(dateStart,dateEnd,volume));
        }
        return 0;
    }
}
