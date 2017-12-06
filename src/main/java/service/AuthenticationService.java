package service;

import dao.AaaDao;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationService {
    private static final Logger logger = LogManager.getLogger(AuthenticationService.class.getName());
    private HashService hashService;
    private AaaDao aaaDao;

    public AuthenticationService(HashService hashService, AaaDao aaaDao) {
        this.hashService = hashService;
        this.aaaDao = aaaDao;
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
}
