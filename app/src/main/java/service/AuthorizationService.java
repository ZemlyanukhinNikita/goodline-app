package service;

import dao.AaaDao;
import domain.Roles;
import domain.User;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthorizationService {
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
            log.error("role {} invalid", role);
            return 3;
        }

        if (aaaDao.getResourceFromTableResourceUsersRoles(user, role, resource) == null) {
            log.error("resource {} not found by User {}", resource, login);
            return 4;
        } else {
            log.debug("Authorization is successful");
            return 0;
        }
    }
}
