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

    public static void main(String[] args) throws SQLException, ParseException {
        logger.debug("Start application.");
        ArrayList<Accounting> accounting = new ArrayList<>();

        CmdParserService cmdParserService = new CmdParserService();
        UserData userData = cmdParserService.cliParse(args);
        DbConnectionService dBconnection = new DbConnectionService();
        int systemExitCode = 0;
        try (Connection connection = dBconnection.getDbConnection()) {
            dBconnection.doMigration();
            AaaDao aaaDao = new AaaDao(connection);
            ValidationService validationService = new ValidationService();
            HashService hashService = new HashService();
            AuthenticationService authenticationService = new AuthenticationService(hashService,aaaDao);
            AuthorizationService authorizationService = new AuthorizationService(aaaDao);
            AccountingService accountingService = new AccountingService(aaaDao,validationService);

            if (userData.isAuthenticated()) {
                logger.debug("Authentication is performed.");
                systemExitCode = authenticationService.authenticate(userData.getLogin(), userData.getPassword());
            }

            if (systemExitCode == 0 && userData.isAuthorized()) {
                logger.debug("Authorization is performed.");
                systemExitCode = authorizationService.authorize(userData.getLogin(), userData.getRole(),
                        userData.getPath());
            }

            if (systemExitCode == 0 && userData.isAccounted()) {
                logger.debug("Accounting is performed.");
                systemExitCode = accountingService.account(userData.getDateStart(), userData.getDateEnd(),
                        userData.getVolume(), accounting);
            }

            if (!userData.isAuthenticated()) {
                logger.debug("Print help.");
                cmdParserService.printHelp();
            }

        } catch (MyException e) {
            logger.error("Database error", e);
            System.exit(255);
        }
        logger.debug("Application is finished");
        System.exit(systemExitCode);
    }
}
