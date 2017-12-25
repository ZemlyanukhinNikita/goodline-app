package service;

import dao.AuthenticationDao;
import dao.AuthorizationDao;
import domain.Roles;
import domain.User;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class AuthorizationService {
    private AuthorizationDao authorizationDao;
    private AuthenticationDao authenticationDao;

    public AuthorizationService(AuthorizationDao authorizationDao, AuthenticationDao authenticationDao) {
        this.authorizationDao = authorizationDao;
        this.authenticationDao = authenticationDao;
    }

    private User getUser(String login) throws MyException, IOException {
        return authenticationDao.getDataFromTableUser(login);
    }

    public int authorize(String login, String role, String resource) throws MyException, IOException {

        User user = getUser(login);
        if (!Roles.isValidRole(role)) {
            log.error("role {} invalid", role);
            return 3;
        }

        if (authorizationDao.getResourceFromTableResourceUsersRoles(user, role, resource) == null) {
            log.error("resource {} not found by User {}", resource, login);
            return 4;
        } else {
            log.debug("Authorization is successful");
            return 0;
        }
    }
}
