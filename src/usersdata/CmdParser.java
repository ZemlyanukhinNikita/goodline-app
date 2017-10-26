package usersdata;

import org.apache.commons.cli.*;

public class CmdParser {
    public static Options options = new Options();
    private static CommandLine cmd;
    private static CommandLineParser parser = new DefaultParser();

    public CmdParser() {
        options.addOption("l", true, "User login");
        options.addOption("p", true, "USer password");
        options.addOption("r", true, "User role");
        options.addOption("pt", true, "User resource");
        options.addOption("ds", true, "Data start");
        options.addOption("de", true, "Data end");
        options.addOption("v", true, "User volume");
        options.addOption("h", false, "Help information");
    }

    public boolean isAuthenticated() {
        return (cmd.hasOption("l") && cmd.hasOption("p"));
    }

    public boolean isAuthorized() {
        return (isAuthenticated() && cmd.hasOption("r") && cmd.hasOption("pt"));
    }

    public boolean isAccounted() {
        return (isAuthorized() && cmd.hasOption("ds")
                && cmd.hasOption("de") && cmd.hasOption("v"));
    }

    public boolean isHelp() {
        return (!isAuthenticated() || cmd.hasOption("h"));
    }

    public static void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Main", options);
    }

    public UserData cliParse(String[] args) throws ParseException {
        cmd = parser.parse(options, args);
        UserData userData = new UserData();
        userData.setLogin(cmd.getOptionValue("l"));
        userData.setPassword(cmd.getOptionValue("p"));
        userData.setRole(cmd.getOptionValue("r"));
        userData.setPath(cmd.getOptionValue("pt"));
        userData.setDateStart(cmd.getOptionValue("ds"));
        userData.setDateEnd(cmd.getOptionValue("de"));
        userData.setVolume(cmd.getOptionValue("v"));

        return userData;
    }
}
