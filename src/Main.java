import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.Roles;
import domain.User;
import org.apache.commons.cli.ParseException;
import service.Aaa;
import service.CmdParser;
import service.Hash;
import service.UserData;

import java.util.ArrayList;

import static service.CmdParser.options;
import static service.CmdParser.printHelp;

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
        resourceUsersRoles.add(new ResourceUsersRoles(5L, 2L, Roles.READ, "DDD"));

        ArrayList<Accounting> accounting = new ArrayList<>();

        CmdParser cmdParser = new CmdParser();
        UserData userData = cmdParser.cliParse(args);

        if (userData.isAuthenticated()) {
            Aaa.authenticate(userData.getLogin(), userData.getPassword(), users);
        }

        if (userData.isAuthorized()) {
            Aaa.authorize(userData.getRole(), userData.getPath(), resourceUsersRoles);
        }

        if (userData.isAccounted()) {
            Aaa.account(userData.getDateStart(), userData.getDateEnd(), userData.getVolume(), accounting);
        }

        if (options.hasOption("h") && !userData.isAuthenticated()) {
            printHelp();
        }
    }
}
