package service;

import dao.AaaDao;
import domain.Accounting;
import domain.Roles;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Aaa {
    private static final Logger logger = LogManager.getLogger(Aaa.class.getName());
    private static AaaDao aaaDao;

    public Aaa(AaaDao aaaDao) {
        Aaa.aaaDao = aaaDao;
    }

    private static User getUser(String login) {
        return aaaDao.getDataFromTableUser(login);
    }

    public int authenticate(String login, String pass) {
        User user = getUser(login);
        if (aaaDao.getDataFromTableUser(login) == null) {
            logger.error("login " + login + " not found in the database");
            return 1;
        }
        if (aaaDao.getDataFromTableUser(login) != null && !Hash.isRightHashPassword(pass, user.getPassword(), user.getSalt())) {
            logger.error("password " + pass + " for User " + login + "not found in the database");
            return 2;
        }
        return 0;
    }

    public int authorize(String login, String role, String resource) {

        User user = getUser(login);
        if (!Roles.isValidRole(role)) {
            logger.error("role " + role + " invalid");
            return 3;
        }

        if (aaaDao.getResourceFromTableResourceUsersRoles(user, role, resource) != null) {
            System.out.println(aaaDao.getResourceFromTableResourceUsersRoles(user, role, resource));
            return 0;
        }

        logger.error("resource " + resource + " not found by User " + login);
        return 4;
    }

    public int account(String dateStart, String dateEnd, String volume,
                       ArrayList<Accounting> accounting) {
        //Проверка валидности объема и дат.
        if ((!Validation.isValidVolume(volume)) || (!Validation.isValidDate(dateStart))
                || (!Validation.isValidDate(dateEnd))) {
            logger.error("Invalid dates or volume");
            return 5;
        } else {
            accounting.add(new Accounting(dateStart, dateEnd, volume));
            aaaDao.setDataToTableAccounting(new Accounting(dateStart, dateEnd, volume));
        }
        return 0;
    }
}
