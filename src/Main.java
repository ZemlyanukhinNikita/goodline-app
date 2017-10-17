import java.util.ArrayList;

/**
 * Created by Nikita Zemlyanukhin on 11.10.2017.
 */
public class Main {
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


    public static void main(String[] args) {
        //Коллекция пользователей
        ArrayList<User> Users = new ArrayList<User>();
        Users.add(new User((long)1,"Vasya","qwerty"));
        Users.add(new User((long)2,"Vasya123","123"));

        ArrayList<ResourceUsersRoles> ResUserRoles = new ArrayList<ResourceUsersRoles>();
        ResUserRoles.add(new ResourceUsersRoles((long)1,(long)1,Roles.READ,"A.B"));
        ResUserRoles.add(new ResourceUsersRoles((long)2,(long)2,Roles.EXECUTE,"H.I.J"));
        String login = args[0];
        String password = args[1];
        //Authentication
        if (args.length < 2)  {
            System.out.println("Not enough data transmitted");
        }
       if (args.length == 2){

            for(int i = 0; i < Users.size(); i++){
                if((login.equals(Users.get(i).getLogin()) && password.equals(Users.get(i).getPassword()))){
                    //System.out.println("0");}
                    System.exit(0);}

            }
            isCorrectLoginPassword(login,password,Users);

        }
        boolean flag=false;
       if(args.length>=3) {

            isCorrectLoginPassword(login,password,Users);
            for (int i = 0; i < Users.size(); i++) {
                for (int j = 0; j < ResUserRoles.size(); j++) {
                    if((Users.get(i).getId().equals(ResUserRoles.get(j).getUser_id())) && login.equals(Users.get(i).getLogin()) && password.equals(Users.get(i).getPassword())){
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
        boolean flagRes=false;
        for (int j = 0; j <ResUserRoles.size() ; j++){
        for (int i = 0; i < Users.size(); i++) {

                if (login.equals(Users.get(i).getLogin()) && password.equals(Users.get(i).getPassword())) {
                    if(Users.get(i).getId().equals(ResUserRoles.get(i).getUser_id())){
                       // System.out.println(Users.get(i).getId());
                       // System.out.println(ResUserRoles.get(j).getUser_id());
                        if(args[3].equals(ResUserRoles.get(i).getPath()))
                            flagRes=true;
                    }
                }
            }
        } if(!flagRes){System.exit(4);}




    }
}
