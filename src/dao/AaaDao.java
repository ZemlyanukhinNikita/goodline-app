package dao;

import domain.Accounting;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AaaDao {
    private static final Logger logger = LogManager.getLogger(AaaDao.class.getName());
    private Connection connection;

    public AaaDao(Connection connection) {
        if (connection != null) {
            this.connection = connection;
        }
    }

    private Connection getConnection() {
        return connection;
    }

    public User getDataFromTableUser(String login) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM USER WHERE LOGIN = ?")) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getLong("ID"),
                            resultSet.getString("LOGIN"),
                            resultSet.getString("PASSWORD"),
                            resultSet.getString("SALT"));
                }
            }

        } catch (SQLException e) {
            logger.error("request failed", e);
        }
        return null;
    }

    public String getResourceFromTableResourceUsersRoles(User user, String role, String path) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "SELECT PATH FROM RESOURCE_USERS_ROLES WHERE USER_ID = ? AND ROLE = ? AND PATH || '.'" +
                        " LIKE LEFT(? ||'.', LENGTH(PATH || '.'))")) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, role);
            preparedStatement.setString(3, path);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    return resultSet.getString("PATH");
                }
            }
        } catch (SQLException e) {
            logger.error("request failed", e);
        }
        return null;
    }

    public void setDataToTableAccounting(Accounting accounting) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "INSERT INTO ACCOUNTING (START_DATE, END_DATE, VOLUME) VALUES (?,?,?)")) {
            preparedStatement.setString(1, accounting.getDateStart());
            preparedStatement.setString(2, accounting.getDateEnd());
            preparedStatement.setLong(3, Long.parseLong(accounting.getVolume()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("request failed", e);
        }
    }
}
