import java.util.ArrayList;
import java.time.*;

/**
 * Created by Nikita Zemlyanukhin on 11.10.2017.
 */
public class Main {

    public static boolean isValidVolume(String v) {
        try {
            int Volume = Integer.parseInt(v);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isValidDate(String d) {
        try {
            LocalDate data = LocalDate.parse(d);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void isCorrectLoginPassword(String log,String pass,ArrayList<User> Users) {

        for(int i = 0; i < Users.size(); i++){
            if(log.equals(Users.get(i).getLogin())){
                if(!pass.equals(Users.get(i).getPassword())){
                    System.exit(2);
                }}
        }
        boolean l = false;
        for(int i = 0; i < Users.size(); i++){
            //System.out.println(Users.get(i).getLogin());
           // System.out.println(log);
            if(log.equals(Users.get(i).getLogin())){l=true;}
            } if (!l) { System.exit(1);}
        }

        private static boolean isCorrectPath(String argPath, String path) {
            String Arg = new String(argPath);
            String Pth = new String(path);
            String [] Arg1 = Arg.split("\\.");
            String [] Pth1 = Pth.split("\\.");
            if(Arg1.length<Pth1.length) {
                return false;
            } else {
                for (int i = 0; i < Pth1.length; i++) {
                    //System.out.println(Arg1[i]);

                    //System.out.println(Str1[i]);
                    //System.out.println("\\\\");
                    if(!Arg1[i].equals(Pth1[i])){
                        return false;
                    }
                }
            }
            return true;
            // System.out.println(Arg1.length);
            // System.out.println(Str1.length);

            // p=true;
            //System.out.println(p);
    }

    public static void main(String[] args) {
        //Коллекция пользователей
        ArrayList<User> Users = new ArrayList<User>();
        Users.add(new User((long)1,"Vasya","qwerty"));
        Users.add(new User((long)2,"Vasya123","123"));

        ArrayList<ResourceUsersRoles> ResUserRoles = new ArrayList<ResourceUsersRoles>();
        ResUserRoles.add(new ResourceUsersRoles((long)1,(long)1,Roles.READ,"A.B"));
        ResUserRoles.add(new ResourceUsersRoles((long)2,(long)2,Roles.EXECUTE,"H.I.J"));
        //String login = args[0];
        //String password = args[1];
        //Authentication
        if (args.length < 2)  {
            System.out.println("Not enough data transmitted");
        }
       if (args.length == 2){

            for(int i = 0; i < Users.size(); i++){
                if((args[0].equals(Users.get(i).getLogin()) && args[1].equals(Users.get(i).getPassword()))){
                    //System.out.println("0");}
                    System.exit(0);}

            }
            isCorrectLoginPassword(args[0],args[1],Users);

        }
        boolean flag=false;
       if(args.length>=3) {

            isCorrectLoginPassword(args[0],args[1],Users);
            for (int i = 0; i < Users.size(); i++) {
                for (int j = 0; j < ResUserRoles.size(); j++) {
                    if((Users.get(i).getId().equals(ResUserRoles.get(j).getUser_id())) && args[0].equals(Users.get(i).getLogin()) && args[1].equals(Users.get(i).getPassword())){
                       // System.out.println(Users.get(i).getId());
                       // System.out.println(ResUserRoles.get(j).getUser_id());
                      //  System.out.println(args[2]);
                       // System.out.println(ResUserRoles.get(j).getRole());
                        if(args[2].equals(ResUserRoles.get(j).getRole().toString())){
                            flag=true;
                        }

                    }
                }

            }
            if(!flag){System.exit(3);}
        }
        if(args.length==4) {
            boolean flagRes = false;
            boolean correctPath = false;
            int k = 0;
            for (int j = 0; j < ResUserRoles.size(); j++) {
                for (int i = 0; i < Users.size(); i++) {

                    if (args[0].equals(Users.get(i).getLogin()) && args[1].equals(Users.get(i).getPassword())) {
                        if (Users.get(i).getId().equals(ResUserRoles.get(i).getUser_id())) {
                            // System.out.println(Users.get(i).getId());
                            // System.out.println(ResUserRoles.get(j).getUser_id());
                            if (args[3].equals(ResUserRoles.get(i).getPath()))
                                flagRes = true;
                            k = i;
                        }
                    }
                }

            }
            correctPath = isCorrectPath(args[3], ResUserRoles.get(k).getPath());
            if (!correctPath && !flagRes) {
                System.exit(4);
            }
        }
       // System.out.println(args.length);
        if(args.length==7) {
            boolean validDateStart = isValidDate(args[4]);
            boolean validDateEnd = isValidDate(args[5]);
            if (!validDateStart || !validDateEnd) {
                System.exit(5);
            }

            boolean validVolume = isValidVolume(args[6]);
            if (!validVolume) {
                System.exit(5);
            }
        }


    }
}
