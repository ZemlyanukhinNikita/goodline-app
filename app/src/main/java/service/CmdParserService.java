package service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.*;

@Log4j2
public class CmdParserService {
    private Options options = new Options();

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
        formatter.printHelp("Main", options);
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

        log.debug("You entered data {}", userData.toString());

        return userData;
    }
}
