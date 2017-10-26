import org.apache.commons.cli.ParseException;
import usersdata.ResourceUsersRoles;
import usersdata.Roles;
import usersdata.User;
import usersdata.UserData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import static java.lang.Integer.toHexString;

public class Main {
    private static boolean isRightHashPassword(String pass, String userPass, String salt) {
        return (Hash.getHash(Hash.getHash(pass + salt))
                .equals(Hash.getHash(Hash.getHash(userPass + salt))));
    }

    private static void authenticate(String login, String pass, ArrayList<User> users) {
        //По коллеции User сравниваем логин и пароль с командной строки с логином и паролем пользователя из коллекции
        boolean isRightLogin = false;
        for (User User : users) {

            if (login.equals(User.getLogin())) {
                isRightLogin = true;

                if (!isRightHashPassword(pass, User.getPassword(), User.getSalt())) {
                    System.exit(2);
                }
            }

            if ((login.equals(User.getLogin()))
                    && (isRightHashPassword(pass, User.getPassword(), User.getSalt()))) {
                break;
            }
        }

        if (!isRightLogin) {
            System.exit(1);
        }
    }

    private static void authorize(String log, String role, String resource,
                                  ArrayList<User> users, ArrayList<ResourceUsersRoles> resourceUsersRoles) {
        //Проверка валидности роли
        if (!Validation.isValidRole(role)) {
            System.exit(3);
        }
        //По коллекции User и ResourceUserRoles сравниваем логин, ID пользователя, роль и проверяем на дочерний ресурс
        boolean isRightResource = false;
        for (User User : users) {
            for (ResourceUsersRoles ResUserRole : resourceUsersRoles) {

                if ((log.equals(User.getLogin()))
                        && (User.getId().equals(ResUserRole.getUserId()))
                        && (role.equals(ResUserRole.getRole()))
                        && (Validation.isCorrectPath(resource, ResUserRole.getPath()))) {
                    isRightResource = true;
                }
            }
        }

        if (!isRightResource) {
            System.exit(4);
        }
    }

    private static void account(String sd, String ed, String vol) {

        if ((!Validation.isValidVolume(vol)) || !Validation.isValidDate(sd)
                || !Validation.isValidDate(ed)) {
            System.exit(5);
        }
    }

    public static void main(String[] args) throws ParseException {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User((long) 1, "Vasya", "qwerty", Hash.getSalt()));
        users.add(new User((long) 2, "Vasya123", "123", Hash.getSalt()));

        ArrayList<ResourceUsersRoles> resourceUsersRoles = new ArrayList<>();
        resourceUsersRoles.add(new ResourceUsersRoles((long) 1, (long) 1, Roles.READ, "A.B"));
        resourceUsersRoles.add(new ResourceUsersRoles((long) 2, (long) 1, Roles.READ, "H.I.J"));
        resourceUsersRoles.add(new ResourceUsersRoles((long) 3, (long) 1, Roles.WRITE, "H.I.J"));
        resourceUsersRoles.add(new ResourceUsersRoles((long) 4, (long) 2, Roles.EXECUTE, "H.I.J"));
        resourceUsersRoles.add(new ResourceUsersRoles((long) 5, (long) 2, Roles.EXECUTE, "DDD"));

        UserData userData = new UserData();
        userData.cliParse(args);

        if (userData.isHelp()) {
            UserData.help();
        }

        if (userData.isAuthenticated()) {
            authenticate(userData.getLogin(), userData.getPassword(), users);
        }

        if (userData.isAuthorized()) {
            authorize(userData.getLogin(), userData.getRole(), userData.getPath(), users, resourceUsersRoles);
        }

        if (userData.isAccounted()) {
            account(userData.getDs(), userData.getDe(), userData.getVolume());
        }
    }

    static class Hash {
        static String getHash(String source) {
            //Создаем экземпляр класса
            MessageDigest md5;
            //Создаем строку в которую запишем хешированный пароль
            StringBuilder hexString = new StringBuilder();
            try {
                //Возвращаем объект MessageDigest, который реализует указанный алгоритм md5
                md5 = MessageDigest.getInstance("md5");
                //Сбрасываем дайджест для дальнейшего использования
                md5.reset();
                //Обновляем дайджест, используя указанный массив байтов
                md5.update(source.getBytes());
                //Записываем в messageDigest[] массив байтов полученного хеш значения
                byte messageDigest[] = md5.digest();
                //Конвертируем байты в 16-й формат и записываем в строку
                for (byte aMessageDigest : messageDigest) {
                    hexString.append(toHexString(0xFF & aMessageDigest));
                }
            } catch (NoSuchAlgorithmException e) {
                return "";
            }
            return hexString.toString();
        }

        static String getSalt() {
            StringBuffer hexString = new StringBuffer();
            SecureRandom random = new SecureRandom();
            //Создаем байтовый массив
            byte[] salt = new byte[16];
            //Заполняем рандомными байтами
            random.nextBytes(salt);
            String s = "";
            //Конвертируем байты в 16-й формат и записываем в строку
            for (byte aSalt : salt) {
                s = String.valueOf(hexString.append(toHexString(0xFF & aSalt)));
            }
            return s;
        }
    }
}
