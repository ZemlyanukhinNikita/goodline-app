package customInjections;

import service.DbConnectionService;
import service.MyException;

import javax.servlet.ServletContextEvent;

public class InitializeListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DbConnectionService dbConnectionService = new DbConnectionService();
        try {
            dbConnectionService.doMigration();
        } catch (MyException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
