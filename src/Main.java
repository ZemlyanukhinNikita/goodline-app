import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.time.*;

/**
 * Created by Nikita Zemlyanukhin on 11.10.2017.
 */
public class Main {
    static class Hash {
        public static String GetHash(String source)
        {

            MessageDigest md5 ;
            StringBuffer  hexString = new StringBuffer();

            try {
                md5 = MessageDigest.getInstance("md5");
                md5.reset();
                md5.update(source.getBytes());
                byte messageDigest[] = md5.digest();

                for (int i = 0; i < messageDigest.length; i++) {
                    hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                }
            }
            catch (NoSuchAlgorithmException e)
            {
                return "";
            }
            return hexString.toString();
        }
        public static String GetSalt(){
            StringBuffer  hexString = new StringBuffer();
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String s = "";
            for (int i = 0; i < salt.length; i++) {
                s = String.valueOf(hexString.append(Integer.toHexString(0xFF & salt[i])));
            }
            return s;
        }
    }

    public static boolean isValidVolume(String v) {
        try {
            int Volume = Integer.parseInt(v);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isValidDate(String d) {
        try {
            LocalDate data = LocalDate.parse(d);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void isAuthentication(String log, String pass, ArrayList<User> Users, int ArgLength) {
        //System.out.println(ArgLenght);
        if (ArgLength == 2) {

            for (int i = 0; i < Users.size(); i++) {
                if ((log.equals(Users.get(i).getLogin()) && pass.equals(Users.get(i).getPassword()))) {
                    System.exit(0);
                }

            }

        }
        for (int i = 0; i < Users.size(); i++) {
            if (log.equals(Users.get(i).getLogin())) {
                if (!pass.equals(Users.get(i).getPassword())) {
                    System.exit(2);
                }
            }
        }
        boolean l = false;
        for (int i = 0; i < Users.size(); i++) {

            if (log.equals(Users.get(i).getLogin())) {
                l = true;
            }
        }
        if (!l) {
            System.exit(1);
        }
    }

    private static boolean isCorrectPath(String argPath, String path) {
        String Arg = new String(argPath);
        String Pth = new String(path);
        String[] Arg1 = Arg.split("\\.");
        String[] Pth1 = Pth.split("\\.");
        if (Arg1.length < Pth1.length) {
            return false;
        } else {
            for (int i = 0; i < Pth1.length; i++) {
                if (!Arg1[i].equals(Pth1[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void isAuthorization(String log, String pass,String role,String resource, ArrayList<User> Users,ArrayList<ResourceUsersRoles> ResUserRoles, int ArgLength) {
        isAuthentication(log,pass,Users,ArgLength);
        boolean flag=false;
        for (int i = 0; i < Users.size(); i++) {
            for (int j = 0; j < ResUserRoles.size(); j++) {
                if ((Users.get(i).getId().equals(ResUserRoles.get(j).getUser_id())) && log.equals(Users.get(i).getLogin()) && pass.equals(Users.get(i).getPassword())) {
                    if (role.equals(ResUserRoles.get(j).getRole().toString())) {
                        flag = true;
                    }
                }
            }
        }
        if (!flag) {
            System.exit(3);
        }
        boolean flagRes = false;
        boolean correctPath = false;
        int k = 0;
        for (int j = 0; j < ResUserRoles.size(); j++) {
            for (int i = 0; i < Users.size(); i++) {
                if (log.equals(Users.get(i).getLogin()) && pass.equals(Users.get(i).getPassword())) {
                    if (Users.get(i).getId().equals(ResUserRoles.get(i).getUser_id())) {
                        if (resource.equals(ResUserRoles.get(i).getPath()))
                            flagRes = true;
                            k = i;
                    }
                }
            }
        }
        correctPath = isCorrectPath(resource, ResUserRoles.get(k).getPath());
        if (!correctPath && !flagRes) {
            System.exit(4);
        } else {
            System.exit(0);
        }
    }

    public static void isAccounts(String sd, String ed, String vol){
        boolean validDateStart = isValidDate(sd);
        boolean validDateEnd = isValidDate(ed);
        if (!validDateStart || !validDateEnd) {
            System.exit(5);
        }

        boolean validVolume = isValidVolume(vol);
        if (!validVolume) {
            System.exit(5);
        }

    }

    public static void main(String[] args) {
        int ArgLenght = args.length;
        //Коллекция пользователей
        ArrayList<User> Users = new ArrayList<User>();
        Users.add(new User((long) 1, "Vasya", "qwerty"));
        Users.add(new User((long) 2, "Vasya123", "123"));
        //Коллекция пользователей с ролями и ресурсами
        ArrayList<ResourceUsersRoles> ResUserRoles = new ArrayList<ResourceUsersRoles>();
        ResUserRoles.add(new ResourceUsersRoles((long) 1, (long) 1, Roles.READ, "A.B"));
        ResUserRoles.add(new ResourceUsersRoles((long) 2, (long) 2, Roles.EXECUTE, "H.I.J"));

        if (args.length < 2) {
            System.out.println("Not enough data transmitted");
        }
        //String pass;
        //args[1]=Hash.GetHash(Hash.GetHash(args[1])+Hash.GetSalt());
        //System.out.println(Hash.GetHash(Hash.GetHash(args[1])+Hash.GetSalt()));
        //Аутентификация
        isAuthentication(args[0],args[1],Users,ArgLenght);
        //Авторизация
        if (args.length == 4) {
        isAuthorization(args[0],args[1],args[2],args[3],Users,ResUserRoles,ArgLenght);
        }
        //Аккаунтинг
        if (args.length == 7) {
            isAccounts(args[4],args[5],args[6]);
        }
    }
}
