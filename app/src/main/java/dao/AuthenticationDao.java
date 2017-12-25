package dao;

import com.google.inject.Inject;
import customInjections.ConnectionProvider;
import customInjections.InjectConnection;
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
public class AuthenticationDao {
    private final ConnectionProvider<Connection> connection;

    @Inject
    public AuthenticationDao(@InjectConnection ConnectionProvider<Connection> connection) {
        this.connection = connection;
    }

    public User getDataFromTableUser(String login) throws MyException, IOException {
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM USER WHERE LOGIN = ?")) {
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

    public ArrayList<User> findAllUsers() throws MyException, IOException {
        ArrayList<User> users = new ArrayList<User>();
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM USER")) {
            try (ResultSet rs = prsmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getLong("ID"),
                            rs.getString("LOGIN")));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new MyException("Database error", e);
        }
    }

    public User findUserById(long id) throws MyException, IOException {
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM USER WHERE ID = ? ")) {
            prsmt.setLong(1, id);
            try (ResultSet rs = prsmt.executeQuery()) {
                while (rs.next()) {
                    return new User(rs.getLong("ID"), rs.getString("LOGIN"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new MyException("Database error", e);
        }
    }
}
