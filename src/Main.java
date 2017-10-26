import org.apache.commons.cli.ParseException;
import usersdata.*;

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
        for (User user : users) {
            if (login.equals(user.getLogin())) {
                isRightLogin = true;

                if (!isRightHashPassword(pass, user.getPassword(), user.getSalt())) {
                    System.exit(2);
                }
            }
        }

        if (!isRightLogin) {
            System.exit(1);
        }
    }

    private static void authorize(String log, String role, String resource,
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
                        && (role.equals(resUserRole.getRole()))
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

    private static void account(String dateStart, String dateEnd, String volume) {

        if ((!Validation.isValidVolume(volume)) || (!Validation.isValidDate(dateStart))
                || (!Validation.isValidDate(dateEnd))) {
            System.exit(5);
        }
    }

    public static void main(String[] args) throws ParseException {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User((long) 1, "Vasya", "qwerty", Hash.getSalt()));
        users.add(new User((long) 2, "Vasya123", "123", Hash.getSalt()));

        ArrayList<ResourceUsersRoles> resourceUsersRoles = new ArrayList<>();
        resourceUsersRoles.add(new ResourceUsersRoles(1L, 1L, Roles.READ, "A.B"));
        resourceUsersRoles.add(new ResourceUsersRoles(2L, 1L, Roles.READ, "H.I.J"));
        resourceUsersRoles.add(new ResourceUsersRoles(3L, 1L, Roles.WRITE, "H.I.J"));
        resourceUsersRoles.add(new ResourceUsersRoles(4L, 2L, Roles.EXECUTE, "H.I.J"));
        resourceUsersRoles.add(new ResourceUsersRoles(5L, 2L, Roles.EXECUTE, "DDD"));

        //UserData userData = new UserData();
        CmdParser cmdParser = new CmdParser();
        UserData userData = cmdParser.cliParse(args);

        if (cmdParser.isHelp()) {
            CmdParser.help();
        }

        if (cmdParser.isAuthenticated()) {
            authenticate(userData.getLogin(), userData.getPassword(), users);
        }

        if (cmdParser.isAuthorized()) {
            authorize(userData.getLogin(), userData.getRole(), userData.getPath(), users, resourceUsersRoles);
        }

        if (cmdParser.isAccounted()) {
            account(userData.getDateStart(), userData.getDateEnd(), userData.getVolume());
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
                return null;
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
