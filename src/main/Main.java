package main;

import dao.AaaDao;
import domain.Accounting;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws ParseException, SQLException {
        logger.debug("Start application.");
        ArrayList<Accounting> accounting = new ArrayList<>();

        CmdParser cmdParser = new CmdParser();
        UserData userData = cmdParser.cliParse(args);
        DbConnection dBconnection = new DbConnection();
        int systemExitCode = 0;

        try (Connection connection = dBconnection.getDbConnection()) {
            if (connection != null) {
                AaaDao aaaDao = new AaaDao(dBconnection.getDbConnection());
                Validation validation = new Validation();
                Hash hash = new Hash();
                Aaa aaa = new Aaa(aaaDao, validation, hash);

                if (userData.isAuthenticated()) {
                    logger.debug("Authentication is performed.");
                    systemExitCode = aaa.authenticate(userData.getLogin(), userData.getPassword());
                }

                if (systemExitCode == 0 && userData.isAuthorized()) {
                    logger.debug("Authorization is performed.");
                    systemExitCode = aaa.authorize(userData.getLogin(), userData.getRole(), userData.getPath());
                }

                if (systemExitCode == 0 && userData.isAccounted()) {
                    logger.debug("Accounting is performed.");
                    systemExitCode = aaa.account(userData.getDateStart(), userData.getDateEnd(), userData.getVolume(), accounting);
                }

                if (!userData.isAuthenticated()) {
                    logger.debug("Print help.");
                    cmdParser.printHelp();
                }
            } else {
                logger.error("Connection failed");
                System.exit(255);
            }
        } catch (SQLException e) {
            logger.error("Database error", e);
        }
        logger.debug("Application is finished");
        System.exit(systemExitCode);
    }
}
