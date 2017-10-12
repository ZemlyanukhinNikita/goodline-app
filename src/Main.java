import java.util.ArrayList;

/**
 * Created by Nikita Zemlyanukhin on 11.10.2017.
 */
public class Main {
    public static void main(String[] args) {
        //Коллекция пользователей
        ArrayList<User> Users = new ArrayList<User>();
        Users.add(new User((long)1,"Vasya","qwerty"));
        Users.add(new User((long)2,"Vasya123","123"));
        //Коллекция ресурсов
        ArrayList<Resources> UsersResources = new ArrayList<Resources>();
        UsersResources.add(new Resources((long)1,"A.B"));
        UsersResources.add(new Resources((long)2,"H.I.J"));

        ArrayList<ResourceUsersRoles> ResUserRoles = new ArrayList<ResourceUsersRoles>();
        ResUserRoles.add(new ResourceUsersRoles((long)1,(long)1,(long)1,Roles.READ));
        ResUserRoles.add(new ResourceUsersRoles((long)2,(long)2,(long)2,Roles.EXECUTE));

        if (args.length < 2) {
            System.out.println("Not enough data transmitted");
        }
        else if (args.length >= 2){
            String login = args[0];
            String password = args[1];
            for(int i = 0; i < Users.size(); i++){
                if((login.equals(Users.get(i).getLogin()) && password.equals(Users.get(i).getPassword()))){
                    //System.out.println("0");}
                    System.exit(0);}

            }
            for(int i = 0; i < Users.size(); i++){
                if(login.equals(Users.get(i).getLogin())){
                    if(password!=(Users.get(i).getPassword())){
                        System.exit(2);
                    }}
            }
            for(int i = 0; i < Users.size(); i++){
                if(login!=(Users.get(i).getLogin())) {
                    System.exit(1);
                }
            }
        }

        /*if(args.length>=3) {
            for (int i = 0; i < Users.size(); i++) {
                for (int j = 0; j < ResUserRoles.size(); j++) {
                    if(Users.get(i).getId()==(ResUserRoles.get(j).getUser_id())){
                        System.out.println(Users.get(i).getId());
                        System.out.println(ResUserRoles.get(j).getUser_id());
                        System.out.println(args[2]);
                        System.out.println(ResUserRoles.get(j).getRole());
                        if(args[2].equals(ResUserRoles.get(j).getRole().toString())){
                            System.exit(0);
                            //i++;
                            //j++;
                        }


                    }
                }

            }
        }*/

    }
}
