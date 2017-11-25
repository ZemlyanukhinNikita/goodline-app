package service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionService {
    private static final Logger logger = LogManager.getLogger(DbConnectionService.class.getName());
    private static final String PATH_TO_PROPERTIES = "/config.properties";
    private static final String DRIVER = "DRIVER";
    private static final String URL = "URL";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";

    public Connection getDbConnection() throws MyException {
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными
        Properties prop = new Properties();
        try (InputStream fileInputStream = this.getClass().getResourceAsStream(PATH_TO_PROPERTIES)) {
            prop.load(fileInputStream);
            String driver = prop.getProperty(DRIVER);
            String url = prop.getProperty(URL);
            Class.forName(driver);
            return DriverManager.getConnection(url, System.getenv(LOGIN), System.getenv(PASSWORD));
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Class not found ", e);
            logger.error("No connection to the database.");
            throw new MyException("Database error", e);
        } catch (IOException e) {
            logger.error("File not found ", e);
            logger.error("No connection to the database.");
            throw new MyException("File not found", e);
        }
    }

    public void doMigration() throws MyException {
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными
        Properties prop = new Properties();
        try (InputStream fileInputStream = this.getClass().getResourceAsStream(PATH_TO_PROPERTIES)) {
            prop.load(fileInputStream);
            String url = prop.getProperty(URL);
            // Create the Flyway instance
            Flyway flyway = new Flyway();
            // Point it to the database
            flyway.setDataSource(url, System.getenv(LOGIN), System.getenv(PASSWORD));
            // Start the migration
            logger.debug("Do migrations");
            flyway.migrate();
        } catch (IOException e) {
            logger.error("File not found ", e);

        } catch (Exception e) {
            logger.error("No connection to the database.");
            throw new MyException("Database error", e);
        }
    }
}
