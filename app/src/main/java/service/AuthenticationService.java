package service;

import dao.AaaDao;
import domain.User;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthenticationService {
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
            log.error("login {} not found in the database", login);
            return 1;
        } else if (!hashService.isRightHashPassword(pass, user.getPassword(), user.getSalt())) {
            log.error("password {} for User {} not found in the database", pass, login);
            return 2;
        } else {
            log.debug("Authentication is successful");
            return 0;
        }
    }
}
