import org.apache.commons.cli.ParseException;
import usersdata.*;

import java.util.ArrayList;

public class Main {

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

        CmdParser cmdParser = new CmdParser();
        UserData userData = cmdParser.cliParse(args);

        if (cmdParser.isHelp()) {
            CmdParser.help();
        }

        if (cmdParser.isAuthenticated()) {
            Aaa.authenticate(userData.getLogin(), userData.getPassword(), users);
        }

        if (cmdParser.isAuthorized()) {
            Aaa.authorize(userData.getLogin(), userData.getRole(), userData.getPath(), users, resourceUsersRoles);
        }

        if (cmdParser.isAccounted()) {
            Aaa.account(userData.getDateStart(), userData.getDateEnd(), userData.getVolume());
        }
    }

}
