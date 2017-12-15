package dao;

import domain.Accounting;
import domain.ResourceUsersRoles;
import domain.User;
import lombok.extern.log4j.Log4j2;
import service.MyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class AaaDao {
    private Connection connection;

    public AaaDao(Connection connection) {
        this.connection = connection;
    }

    public User getDataFromTableUser(String login) throws MyException {
        try (PreparedStatement prsmt = connection.prepareStatement("SELECT * FROM USER WHERE LOGIN = ?")) {
            prsmt.setString(1, login);
            try (ResultSet rs = prsmt.executeQuery()) {
                if (rs.next()) {
                    log.debug("request is successful");
                    return new User(rs.getLong("ID"),
                            rs.getString("LOGIN"),
                            rs.getString("PASSWORD"),
                            rs.getString("SALT"));
                } else {
                    log.error("request failed");
                    return null;
                }
            }

        } catch (SQLException e) {
            log.error("Database error", e);
            throw new MyException("Database error");
        }
    }

    public ResourceUsersRoles getResourceFromTableResourceUsersRoles(User user, String role, String path) throws MyException {
        try (PreparedStatement prsmt = connection.prepareStatement(
                "SELECT * FROM RESOURCE_USERS_ROLES WHERE USER_ID = ? AND ROLE = ? AND PATH || '.'" +
                        " LIKE LEFT(? ||'.', LENGTH(PATH || '.'))")) {
            prsmt.setLong(1, user.getId());
            prsmt.setString(2, role);
            prsmt.setString(3, path);
            try (ResultSet rs = prsmt.executeQuery()) {
                if (rs.next()) {
                    log.debug("request is successful");
                    return new ResourceUsersRoles(rs.getLong("ID"),
                            rs.getLong("USER_ID"),
                            rs.getString("ROLE"),
                            rs.getString("PATH"));
                } else {
                    log.error("request failed");
                    return null;
                }
            }
        } catch (SQLException e) {
            log.error("Database error");
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
            log.debug("data successfully adding");
        } catch (SQLException e) {
            log.error("request failed", e);
        }
    }
}
