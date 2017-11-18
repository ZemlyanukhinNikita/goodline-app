package service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static final Logger logger = LogManager.getLogger(DbConnection.class.getName());
    private static final String PATH_TO_PROPERTIES = "./resources/config.properties";

    public static Connection getDbConnection() {
        FileInputStream fileInputStream;
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными
        Properties prop = new Properties();
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, System.getenv("login"), System.getenv("password"));
            doMigration();
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Class not found " + e);
        } catch (IOException e) {
            logger.error("File not found ", e);
        }
        logger.error("No connection to the database.");
        return null;
    }

    private static void doMigration() {
        // Create the Flyway instance
        Flyway flyway = new Flyway();
        // Point it to the database
        flyway.setDataSource("jdbc:h2:file:./aaa;mv_store=false", System.getenv("login"), System.getenv("password"));
        // Start the migration
        logger.debug("Do migrations");
        flyway.migrate();
    }
}
