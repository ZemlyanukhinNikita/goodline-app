package service;

import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.Roles;
import domain.User;

import java.util.ArrayList;

public class Aaa {
    public static void authenticate(String login, String pass, ArrayList<User> users) {
        //По коллеции User сравниваем логин и пароль с командной строки с логином и паролем пользователя из коллекции
        boolean isRightLogin = false;
        for (User user : users) {
            if (login.equals(user.getLogin())
                    && !Hash.isRightHashPassword(pass, user.getPassword(), user.getSalt())) {
                System.exit(2);
            }

            if (login.equals(user.getLogin())) {
                isRightLogin = true;
                break;
            }
        }

        if (!isRightLogin) {
            System.exit(1);
        }
    }

    public static void authorize(String log, String role, String resource,
                                 ArrayList<User> users, ArrayList<ResourceUsersRoles> resourceUsersRoles) {
        //Проверка валидности роли
        if (!Roles.isValidRole(role)) {
            System.exit(3);
        }
        //По коллекции User и ResourceUserRoles сравниваем логин, ID пользователя, роль и проверяем на дочерний ресурс
        boolean isRightResource = false;
        for (User user : users) {
            for (ResourceUsersRoles resUserRole : resourceUsersRoles) {
                if ((log.equals(user.getLogin()))
                        && (user.getId().equals(resUserRole.getUserId()))
                        && (role.equals(resUserRole.getRoleName()))
                        && (Validation.isCorrectPath(resource, resUserRole.getPath()))) {
                    isRightResource = true;
                    break;
                }
            }
        }

        if (!isRightResource) {
            System.exit(4);
        }
    }

    public static void account(String dateStart, String dateEnd, String volume, ArrayList<Accounting> accountings) {

        if ((!Validation.isValidVolume(volume)) || (!Validation.isValidDate(dateStart))
                || (!Validation.isValidDate(dateEnd))) {
            System.exit(5);
        }
        else {
            accountings.add(new Accounting(dateStart,dateEnd,volume));
        }
    }
}
