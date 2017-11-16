package dao;

import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.Roles;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AaaDao {
    private static final Logger logger = LogManager.getLogger(AaaDao.class.getName());
    private static Connection connection;

    public AaaDao(Connection connection) {
        AaaDao.connection = connection;
    }

    private static Connection getConnection() {
        return connection;
    }

    public static User getDataFromTableUser(String login) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            if (getConnection() != null) {
                preparedStatement = getConnection().prepareStatement("SELECT * FROM USER WHERE LOGIN = ?");
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    return new User(resultSet.getLong("ID"),
                            resultSet.getString("LOGIN"),
                            resultSet.getString("PASSWORD"),
                            resultSet.getString("SALT"));
                }
            }
        } catch (SQLException e) {
            logger.error("request failed", e);
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return null;
    }

    public static ArrayList<ResourceUsersRoles> getDataFromTableResourceUsersRoles(User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<ResourceUsersRoles> resourceUsersRoles = new ArrayList<>();
        try {
            if (getConnection() != null) {
                preparedStatement = getConnection().prepareStatement("SELECT * FROM RESOURCE_USERS_ROLES WHERE USER_ID = ?");
                preparedStatement.setLong(1, user.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resourceUsersRoles.add(new ResourceUsersRoles(resultSet.getLong("ID"),
                            resultSet.getLong("USER_ID"),
                            Roles.valueOf(resultSet.getString("ROLE")),
                            resultSet.getString("PATH")));
                }
                return resourceUsersRoles;
            }
        } catch (SQLException e) {
            logger.error("request failed", e);
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return null;
    }

    public static void setDataToTableAccounting(Accounting accounting) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            if (getConnection() != null) {
                preparedStatement = getConnection().prepareStatement(
                        "INSERT INTO ACCOUNTING (START_DATE, END_DATE, VOLUME) VALUES (?,?,?)");
                preparedStatement.setString(1, accounting.getDateStart());
                preparedStatement.setString(2, accounting.getDateEnd());
                preparedStatement.setLong(3, Long.parseLong(accounting.getVolume()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("request failed", e);
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
