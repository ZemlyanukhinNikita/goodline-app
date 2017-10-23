import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import static java.lang.Integer.toHexString;

public class Main {
    private static boolean isRightHashPassword(String pass, String userPass, String salt) {
        return Hash.getHash(Hash.getHash(pass + salt)).equals(Hash.getHash(Hash.getHash(userPass + salt)));
    }
    private static void authenticate(String log, String pass, ArrayList<User> Users) {
        //По коллеции User сравниваем логин и пароль с командной строки с логином и паролем пользователя из коллекции
        for (User User : Users) {

            if ((log.equals(User.getLogin()) && (isRightHashPassword(pass,User.getPassword(),User.getSalt())))) {
                break;
            }
        }
        //По коллекции User смотрим неправильный пароль
        for (User User : Users) {

            if (log.equals(User.getLogin())) {

                if (!isRightHashPassword(pass,User.getPassword(),User.getSalt())) {
                    System.exit(2);
                }
            }
        }
        boolean l = false;
        for (User User : Users) {

            if (log.equals(User.getLogin())) {
                l = true;
            }
        }

        if (!l) {
            System.exit(1);
        }
    }
    private static void authorize(String log, String pass, String role, String resource,
                                  ArrayList<User> Users, ArrayList<ResourceUsersRoles> ResUserRoles) {
        authenticate(log, pass, Users);
        for (User User : Users) {
            for (ResourceUsersRoles ResUserRole : ResUserRoles) {

                if ((User.getId().equals(ResUserRole.getUser_id())) && (log.equals(User.getLogin())) && (!role.equals(ResUserRole.getRole().toString()))) {
                        System.exit(3);
                }
            }
        }

        boolean flagRes = false;
        boolean correctPath;
        int k = 0;
        for (int j = 0; j < ResUserRoles.size(); j++) {
            for (int i = 0; i < Users.size(); i++) {

                if (log.equals(Users.get(i).getLogin())) {

                    if ((Users.get(i).getId()).equals(ResUserRoles.get(i).getUser_id())) {
                        k = i;
                        if (resource.equals(ResUserRoles.get(i).getPath())) {
                            flagRes = true;
                        }

                    }
                }
            }
        }
        correctPath = Validation.isCorrectPath(resource, ResUserRoles.get(k).getPath());
        if (!correctPath && !flagRes) {
            System.exit(4);
        }
    }
    private static void account(String log, String pass, String role, String resource, String sd, String ed, String vol,
                                 ArrayList<User> Users, ArrayList<ResourceUsersRoles> ResUsersRoles) {
        authorize(log, pass, role, resource, Users, ResUsersRoles);
        boolean validDateStart = Validation.isValidDate(sd);
        boolean validDateEnd = Validation.isValidDate(ed);
        if (!validDateStart || !validDateEnd) {
            System.exit(5);
        }
        boolean validVolume = Validation.isValidVolume(vol);
        if (!validVolume) {
            System.exit(5);
        }
    }

    public static void main(String[] args) {
        //Коллекция пользователей
        ArrayList<User> Users = new ArrayList<>();
        Users.add(new User((long) 1, "Vasya", "qwerty", Main.Hash.getSalt()));
        Users.add(new User((long) 2, "Vasya123", "123", Main.Hash.getSalt()));
        //Коллекция пользователей с ролями и ресурсами
        ArrayList<ResourceUsersRoles> ResUserRoles = new ArrayList<>();
        ResUserRoles.add(new ResourceUsersRoles((long) 1, (long) 1, Roles.READ, "A.B"));
        ResUserRoles.add(new ResourceUsersRoles((long) 2, (long) 2, Roles.EXECUTE, "H.I.J"));
        ResUserRoles.add(new ResourceUsersRoles((long) 3, (long) 1, Roles.READ, "H.I.J"));

        UserData userData = new UserData();
        userData.cliParser(args);

        if (userData.isHelp()) {
            UserData.help();
        }

        if (userData.isAuthentication()) {
            authenticate(userData.getLogin(), userData.getPassword(), Users);
        }

        if (userData.isAuthorization()) {
            authorize(userData.getLogin(), userData.getPassword(), userData.getRole(), userData.getPath(),
                    Users, ResUserRoles);
        }

        if (userData.isAccounts()) {
            account(userData.getLogin(), userData.getPassword(), userData.getRole(), userData.getPath(),
                    userData.getDs(), userData.getDe(), userData.getVolume(), Users, ResUserRoles);
        }
    }
    static class Hash {
        static String getHash(String source) {
            MessageDigest md5;
            StringBuilder hexString = new StringBuilder();
            try {
                md5 = MessageDigest.getInstance("md5");
                md5.reset();
                md5.update(source.getBytes());
                byte messageDigest[] = md5.digest();

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
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String s = "";
            for (byte aSalt : salt) {
                s = String.valueOf(hexString.append(toHexString(0xFF & aSalt)));
            }
            return s;
        }
    }
}
