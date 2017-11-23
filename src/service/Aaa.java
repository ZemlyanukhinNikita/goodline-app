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
    private AaaDao aaaDao;
    private Validation validation;
    private Hash hash;

    public Aaa(AaaDao aaaDao, Validation validation, Hash hash) {
        this.aaaDao = aaaDao;
        this.validation = validation;
        this.hash = hash;
    }

    private User getUser(String login) throws MyException {
        return aaaDao.getDataFromTableUser(login);
    }

    public int authenticate(String login, String pass) throws MyException {
        User user = getUser(login);
        if (aaaDao.getDataFromTableUser(login) == null) {
            logger.error("login {} not found in the database", login);
            return 1;
        } else if (!hash.isRightHashPassword(pass, user.getPassword(), user.getSalt())) {
            logger.error("password {} for User {} not found in the database", pass, login);
            return 2;
        } else {
            logger.debug("Authentication is successful");
            return 0;
        }
    }

    public int authorize(String login, String role, String resource) throws MyException {

        User user = getUser(login);
        if (!Roles.isValidRole(role)) {
            logger.error("role {} invalid", role);
            return 3;
        }

        if (aaaDao.getResourceFromTableResourceUsersRoles(user, role, resource) == null) {
            logger.error("resource {} not found by User {}", resource, login);
            return 4;
        } else {
            logger.debug("Authorization is successful");
            return 0;
        }
    }

    public int account(String dateStart, String dateEnd, String volume,
                       ArrayList<Accounting> accounting) {
        //Проверка валидности объема и дат.
        if ((!validation.isValidVolume(volume)) || (!validation.isValidDate(dateStart))
                || (!validation.isValidDate(dateEnd))) {
            logger.error("Invalid dates or volume");
            return 5;
        } else {
            accounting.add(new Accounting(dateStart, dateEnd, volume));
            aaaDao.setDataToTableAccounting(new Accounting(dateStart, dateEnd, volume));
            logger.debug("Accounting is successful");
            return 0;
        }
    }
}
