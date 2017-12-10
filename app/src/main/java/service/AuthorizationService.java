package service;

import dao.AaaDao;
import domain.Roles;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorizationService {
    private static final Logger logger = LogManager.getLogger(AuthorizationService.class.getName());
    private AaaDao aaaDao;

    public AuthorizationService(AaaDao aaaDao) {
        this.aaaDao = aaaDao;
    }

    private User getUser(String login) throws MyException {
        return aaaDao.getDataFromTableUser(login);
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
}
