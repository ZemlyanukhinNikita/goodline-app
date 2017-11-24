package dao;

import domain.Accounting;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AaaDao {
    private static final Logger logger = LogManager.getLogger(AaaDao.class.getName());
    private Connection connection;

    public AaaDao(Connection connection) {
        this.connection = connection;
    }

    public User getDataFromTableUser(String login) throws MyException {
        try (PreparedStatement prsmt = connection.prepareStatement("SELECT * FROM USER WHERE LOGIN = ?")) {
            prsmt.setString(1, login);
            try (ResultSet rs = prsmt.executeQuery()) {
                if (rs.next()) {
                    logger.debug("request is successful");
                    return new User(rs.getLong("ID"),
                            rs.getString("LOGIN"),
                            rs.getString("PASSWORD"),
                            rs.getString("SALT"));
                } else {
                    logger.error("request failed");
                    return null;
                }
            }

        } catch (SQLException e) {
            logger.error("Database error", e);
            throw new MyException("Database error");
        }
    }

    public String getResourceFromTableResourceUsersRoles(User user, String role, String path) throws MyException {
        try (PreparedStatement prsmt = connection.prepareStatement(
                "SELECT PATH FROM RESOURCE_USERS_ROLES WHERE USER_ID = ? AND ROLE = ? AND PATH || '.'" +
                        " LIKE LEFT(? ||'.', LENGTH(PATH || '.'))")) {
            prsmt.setLong(1, user.getId());
            prsmt.setString(2, role);
            prsmt.setString(3, path);
            try (ResultSet rs = prsmt.executeQuery()) {
                if (rs.next()) {
                    logger.debug("request is successful");
                    return rs.getString("PATH");
                } else {
                    logger.error("request failed");
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Database error");
            throw new MyException("Database error", e);
        }
    }

    public void setDataToTableAccounting(Accounting accounting) {
        try (PreparedStatement prsmt = connection.prepareStatement(
                "INSERT INTO ACCOUNTING (START_DATE, END_DATE, VOLUME) VALUES (?,?,?)")) {
            prsmt.setString(1, accounting.getDateStart());
            prsmt.setString(2, accounting.getDateEnd());
            prsmt.setLong(3, Long.parseLong(accounting.getVolume()));
            prsmt.executeUpdate();
            logger.debug("data successfully adding");
        } catch (SQLException e) {
            logger.error("request failed", e);
        }
    }
}
