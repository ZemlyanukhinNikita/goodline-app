package service;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CmdParser {
    private Options options = new Options();
    private static final Logger logger = LogManager.getLogger(CmdParser.class.getName());

    public CmdParser() {
        options
                .addOption("l", true, "User login")
                .addOption("p", true, "USer password")
                .addOption("r", true, "User role")
                .addOption("pt", true, "User resource")
                .addOption("ds", true, "Data start")
                .addOption("de", true, "Data end")
                .addOption("v", true, "User volume")
                .addOption("h", false, "Help information");
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("main.Main", options);
    }

    public UserData cliParse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        UserData userData = new UserData();
        userData.setLogin(cmd.getOptionValue("l"));
        userData.setPassword(cmd.getOptionValue("p"));
        userData.setRole(cmd.getOptionValue("r"));
        userData.setPath(cmd.getOptionValue("pt"));
        userData.setDateStart(cmd.getOptionValue("ds"));
        userData.setDateEnd(cmd.getOptionValue("de"));
        userData.setVolume(cmd.getOptionValue("v"));

        logger.debug("You entered data {}", userData.toString());

        return userData;
    }
}
