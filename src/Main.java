import java.util.ArrayList;

/**
 * Created by Nikita Zemlyanukhin on 11.10.2017.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<User> Users = new ArrayList<User>();
        Users.add(new User((long)1,"Vasya","qwerty"));
        Users.add(new User((long)2,"Vasya123","123"));

        ArrayList<Resources> UsersResources = new ArrayList<Resources>();
        UsersResources.add(new Resources((long)1,"A.B"));
        UsersResources.add(new Resources((long)2,"H.I.J"));


        if(args.length==2){
            String login = args[0];
            String password = args[1];
            for(int i = 0; i < Users.size(); i++){
                if((login.equals(Users.get(i).getLogin()) && password.equals(Users.get(i).getPassword()))){
                    //System.out.println("0");}
                    System.exit(0);}

            }
            for(int i = 0; i < Users.size(); i++){
                if(args[0].equals(Users.get(i).getLogin())){
                    if(args[1]!=(Users.get(i).getPassword())){
                        System.exit(2);
                    }}
            }
            for(int i = 0; i < Users.size(); i++){
                if(args[0]!=(Users.get(i).getLogin())) {
                    System.exit(1);
                }
            }
        } else
            System.out.println("Not enough data transmitted");

    }
}
