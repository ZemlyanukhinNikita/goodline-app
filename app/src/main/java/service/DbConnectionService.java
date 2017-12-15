package service;

import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Log4j2
public class DbConnectionService {
    private static final String PATH_TO_PROPERTIES = "../config.properties";
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
            log.error("Class not found ", e);
            log.error("No connection to the database.");
            throw new MyException("Database error", e);
        } catch (IOException e) {
            log.error("File not found ", e);
            log.error("No connection to the database.");
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
            log.debug("Do migrations");
            flyway.migrate();
        } catch (IOException e) {
            log.error("File not found ", e);

        } catch (Exception e) {
            log.error("No connection to the database.");
            throw new MyException("Database error", e);
        }
    }
}
