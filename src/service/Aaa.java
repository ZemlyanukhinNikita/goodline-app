package service;

import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.Roles;
import domain.User;

import java.util.ArrayList;

public class Aaa {
    public static void authenticate(String login, String pass, ArrayList<User> users) {
        //По коллеции User сравниваем логин и пароль с командной строки с логином и паролем пользователя из коллекции
        for (User user : users) {
            if (login.equals(user.getLogin())
                    && Hash.isRightHashPassword(pass, user.getPassword(), user.getSalt())) {
                return;
            }
            if (login.equals(user.getLogin())
                    && !Hash.isRightHashPassword(pass, user.getPassword(), user.getSalt())) {
                System.exit(2);
            }
        }
        System.exit(1);
    }

    public static void authorize(String role, String resource,
                                 ArrayList<ResourceUsersRoles> resourceUsersRoles) {
        //Проверка валидности роли
        if (!Roles.isValidRole(role)) {
            System.exit(3);
        }

        for (ResourceUsersRoles resUserRole : resourceUsersRoles) {
            if (role.equals(resUserRole.getRole().toString())
                    && Validation.isCorrectPath(resource, resUserRole.getPath()))
                return;
        }
        System.exit(4);
    }

    public static void account(String dateStart, String dateEnd, String volume,
                               ArrayList<Accounting> accounting) {
        //Проверка валидности объема и дат.
        if ((!Validation.isValidVolume(volume)) || (!Validation.isValidDate(dateStart))
                || (!Validation.isValidDate(dateEnd))) {
            System.exit(5);
        } else {
            accounting.add(new Accounting(dateStart, dateEnd, volume));
        }
    }
}
