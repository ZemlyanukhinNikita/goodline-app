package service;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CmdParserService {
    private Options options = new Options();
    private static final Logger logger = LogManager.getLogger(CmdParserService.class.getName());

    public CmdParserService() {
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

    public UserDataService cliParse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        UserDataService userDataService = new UserDataService();
        userDataService.setLogin(cmd.getOptionValue("l"));
        userDataService.setPassword(cmd.getOptionValue("p"));
        userDataService.setRole(cmd.getOptionValue("r"));
        userDataService.setPath(cmd.getOptionValue("pt"));
        userDataService.setDateStart(cmd.getOptionValue("ds"));
        userDataService.setDateEnd(cmd.getOptionValue("de"));
        userDataService.setVolume(cmd.getOptionValue("v"));

        logger.debug("You entered data {}", userDataService.toString());

        return userDataService;
    }
}
