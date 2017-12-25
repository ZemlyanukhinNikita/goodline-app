package service;

import dao.AuthenticationDao;
import domain.User;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class AuthenticationService {
    private HashService hashService;
    private AuthenticationDao authenticationDao;

    public AuthenticationService(HashService hashService, AuthenticationDao authenticationDao) {
        this.hashService = hashService;
        this.authenticationDao = authenticationDao;
    }

    private User getUser(String login) throws MyException, IOException {
        return authenticationDao.getDataFromTableUser(login);
    }

    public int authenticate(String login, String pass) throws MyException, IOException {
        User user = getUser(login);
        if (authenticationDao.getDataFromTableUser(login) == null) {
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
