package main;

import dao.AaaDao;
import domain.Accounting;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Aaa;
import service.CmdParser;
import service.DbConnection;
import service.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static service.CmdParser.options;
import static service.CmdParser.printHelp;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws ParseException, SQLException {
        logger.debug("Start application.");
        ArrayList<Accounting> accounting = new ArrayList<>();

        CmdParser cmdParser = new CmdParser();
        UserData userData = cmdParser.cliParse(args);
        Connection connection = DbConnection.getDbConnection();
        int systemExitCode = 0;
        new AaaDao(connection);

        if (userData.isAuthenticated()) {
            logger.debug("Authentication is performed.");
            systemExitCode = Aaa.authenticate(userData.getLogin(), userData.getPassword());
        }

        if (systemExitCode == 0 && userData.isAuthorized()) {
            logger.debug("Authorization is performed.");
            systemExitCode = Aaa.authorize(userData.getLogin(), userData.getRole(), userData.getPath());
        }

        if (systemExitCode == 0 && userData.isAccounted()) {
            logger.debug("Accounting is performed.");
            systemExitCode = Aaa.account(userData.getDateStart(), userData.getDateEnd(), userData.getVolume(), accounting);
        }

        if (options.hasOption("h") && !userData.isAuthenticated()) {
            logger.debug("Print help.");
            printHelp();
        }
        if (connection != null) {
            connection.close();
        }
        logger.debug("Application is finished");
        System.exit(systemExitCode);
    }
}
