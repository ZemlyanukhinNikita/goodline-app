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
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USER WHERE LOGIN = ?")) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    logger.debug("request is successful");
                    return new User(resultSet.getLong("ID"),
                            resultSet.getString("LOGIN"),
                            resultSet.getString("PASSWORD"),
                            resultSet.getString("SALT"));
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT PATH FROM RESOURCE_USERS_ROLES WHERE USER_ID = ? AND ROLE = ? AND PATH || '.'" +
                        " LIKE LEFT(? ||'.', LENGTH(PATH || '.'))")) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, role);
            preparedStatement.setString(3, path);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    logger.debug("request is successful");
                    return resultSet.getString("PATH");
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO ACCOUNTING (START_DATE, END_DATE, VOLUME) VALUES (?,?,?)")) {
            preparedStatement.setString(1, accounting.getDateStart());
            preparedStatement.setString(2, accounting.getDateEnd());
            preparedStatement.setLong(3, Long.parseLong(accounting.getVolume()));
            preparedStatement.executeUpdate();
            logger.debug("data successfully adding");
        } catch (SQLException e) {
            logger.error("request failed", e);
        }
    }
}
