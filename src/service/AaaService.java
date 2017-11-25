package service;

import dao.AaaDao;
import domain.Accounting;
import domain.Roles;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AaaService {
    private static final Logger logger = LogManager.getLogger(AaaService.class.getName());
    private AaaDao aaaDao;
    private ValidationService validationService;
    private HashService hashService;

    public AaaService(AaaDao aaaDao, ValidationService validationService, HashService hashService) {
        this.aaaDao = aaaDao;
        this.validationService = validationService;
        this.hashService = hashService;
    }

    private User getUser(String login) throws MyException {
        return aaaDao.getDataFromTableUser(login);
    }

    public int authenticate(String login, String pass) throws MyException {
        User user = getUser(login);
        if (aaaDao.getDataFromTableUser(login) == null) {
            logger.error("login {} not found in the database", login);
            return 1;
        } else if (!hashService.isRightHashPassword(pass, user.getPassword(), user.getSalt())) {
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
        if ((!validationService.isValidVolume(volume)) || (!validationService.isValidDate(dateStart))
                || (!validationService.isValidDate(dateEnd))) {
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
