import dao.AccountingDao;
import dao.AuthenticationDao;
import dao.AuthorizationDao;
import domain.Accounting;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.ParseException;
import service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Log4j2
public class Main {

    public static void main(String[] args) throws SQLException, ParseException, IOException, MyException {
        log.debug("Start application.");
        ArrayList<Accounting> accounting = new ArrayList<>();
        CmdParserService cmdParserService = new CmdParserService();
        UserData userData = cmdParserService.cliParse(args);
        DbConnectionService dBconnection = new DbConnectionService();
        int systemExitCode = 0;
        try {
            dBconnection.doMigration();
            AuthenticationDao authenticationDao = new AuthenticationDao(dBconnection);
            AuthorizationDao authorizationDao = new AuthorizationDao(dBconnection, authenticationDao);
            AccountingDao accountingDao = new AccountingDao(dBconnection);
            ValidationService validationService = new ValidationService();
            HashService hashService = new HashService();
            AuthenticationService authenticationService = new AuthenticationService(hashService, authenticationDao);
            AuthorizationService authorizationService = new AuthorizationService(authorizationDao, authenticationDao);
            AccountingService accountingService = new AccountingService(accountingDao, validationService);

            if (userData.isAuthenticated()) {
                log.debug("Authentication is performed.");
                systemExitCode = authenticationService.authenticate(userData.getLogin(), userData.getPassword());
            }

            if (systemExitCode == 0 && userData.isAuthorized()) {
                log.debug("Authorization is performed.");
                systemExitCode = authorizationService.authorize(userData.getLogin(), userData.getRole(),
                        userData.getPath());
            }

            if (systemExitCode == 0 && userData.isAccounted()) {
                log.debug("Accounting is performed.");
                systemExitCode = accountingService.account(userData.getLogin(), userData.getRole(),
                        userData.getPath(), userData.getDateStart(), userData.getDateEnd(), userData.getVolume(), accounting);
            }

            if (!userData.isAuthenticated()) {
                log.debug("Print help.");
                cmdParserService.printHelp();
            }
        } catch (MyException e) {
            log.error("Database error", e);
            System.exit(255);
        }
        log.debug("Application is finished");
        System.exit(systemExitCode);
    }
}
