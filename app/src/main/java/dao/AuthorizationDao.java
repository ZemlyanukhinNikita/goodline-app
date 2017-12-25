package dao;

import com.google.inject.Inject;
import customInjections.ConnectionProvider;
import customInjections.InjectConnection;
import domain.ResourceUsersRoles;
import domain.User;
import lombok.extern.log4j.Log4j2;
import service.MyException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Log4j2
public class AuthorizationDao {
    private final ConnectionProvider<Connection> connection;
    private AuthenticationDao authenticationDao;

    @Inject
    public AuthorizationDao(@InjectConnection ConnectionProvider<Connection> connection,
                            AuthenticationDao authenticationDao) {
        this.connection = connection;
        this.authenticationDao = authenticationDao;
    }

    public ResourceUsersRoles getResourceFromTableResourceUsersRoles(User user, String role, String path) throws MyException, IOException {
        try (PreparedStatement prsmt = connection.get().prepareStatement(
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

    public ArrayList<ResourceUsersRoles> findRoles() throws MyException, IOException {
        ArrayList<ResourceUsersRoles> roles = new ArrayList<ResourceUsersRoles>();
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM RESOURCE_USERS_ROLES")) {
            try (ResultSet rs = prsmt.executeQuery()) {
                while (rs.next()) {
                    roles.add(new ResourceUsersRoles(rs.getLong("ID"),
                            rs.getLong("USER_ID"),
                            rs.getString("ROLE"),
                            rs.getString("PATH")));
                }
                return roles;
            }
        } catch (SQLException e) {
            throw new MyException("Database error", e);
        }
    }

    public ResourceUsersRoles findRolesById(long id) throws MyException, IOException {
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM RESOURCE_USERS_ROLES WHERE ID = ?")) {
            prsmt.setLong(1, id);
            try (ResultSet rs = prsmt.executeQuery()) {
                while (rs.next()) {
                    return new ResourceUsersRoles(rs.getLong("ID"),
                            rs.getLong("USER_ID"),
                            rs.getString("ROLE"),
                            rs.getString("PATH"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new MyException("Database error", e);
        }
    }

    public ArrayList<ResourceUsersRoles> findRolesByUserId(long userId) throws MyException, IOException {
        ArrayList<ResourceUsersRoles> resourceUsersRoles = new ArrayList<>();
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM RESOURCE_USERS_ROLES" +
                " WHERE USER_ID = ?")) {
            prsmt.setLong(1, userId);
            try (ResultSet rs = prsmt.executeQuery()) {
                while (rs.next()) {
                    resourceUsersRoles.add(new ResourceUsersRoles(rs.getLong("ID"),
                            rs.getLong("USER_ID"),
                            rs.getString("ROLE"),
                            rs.getString("PATH")));
                }
                return resourceUsersRoles;
            }
        } catch (SQLException e) {
            throw new MyException("Database error", e);
        }
    }
}
