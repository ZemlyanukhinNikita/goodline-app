package service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final Logger logger = LogManager.getLogger(DbConnection.class.getName());

    public static Connection getDbConnection() {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:file:./aaa;mv_store=false";
            String login = "admin";
            String password = "db_h2_17";
            Connection con = DriverManager.getConnection(url, login, password);
            doMigration();
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Class not found or not connection " + e);
            e.printStackTrace();
        }
        logger.error("No connection to the database.");
        return null;
    }

    private static void doMigration() {
        // Create the Flyway instance
        Flyway flyway = new Flyway();
        // Point it to the database
        flyway.setDataSource("jdbc:h2:file:./aaa;mv_store=false", "admin", "db_h2_17");
        // Start the migration
        logger.debug("Do migrations");
        flyway.migrate();
    }
}
