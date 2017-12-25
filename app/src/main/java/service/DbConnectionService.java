package service;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import customInjections.ConnectionProvider;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Log4j2
public class DbConnectionService implements ConnectionProvider<Connection> {
    private static final String PATH_TO_PROPERTIES = "/config.properties";
    private static final String DRIVER = "DRIVER";
    private static final String URL = "URL";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";

    @Override
    public Connection get() throws IOException, SQLException, MyException {

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        Properties prop = new Properties();
        try (InputStream fileInputStream = this.getClass().getResourceAsStream(PATH_TO_PROPERTIES)) {
            prop.load(fileInputStream);
            cpds.setDriverClass(prop.getProperty(DRIVER));
            cpds.setJdbcUrl(prop.getProperty(URL));
            cpds.setUser(System.getenv(LOGIN));
            cpds.setPassword(System.getenv(PASSWORD));
            // set options
            cpds.setMaxStatements(180);
            cpds.setMaxStatementsPerConnection(180);
            cpds.setMinPoolSize(50);
            cpds.setAcquireIncrement(10);
            cpds.setMaxPoolSize(60);
            cpds.setMaxIdleTime(30);
            return cpds.getConnection();
        } catch (PropertyVetoException e) {
            throw new MyException("Connection error");
        }
    }

    public void doMigration() throws MyException {
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными
        Properties prop = new Properties();
        try (InputStream fileInputStream = this.getClass().getResourceAsStream(PATH_TO_PROPERTIES)) {
            prop.load(fileInputStream);
            // Create the Flyway instance
            Flyway flyway = new Flyway();
            // Off checksum
            flyway.setValidateOnMigrate(false);
            // Point it to the database
            flyway.setDataSource(prop.getProperty(URL), System.getenv(LOGIN), System.getenv(PASSWORD));
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
