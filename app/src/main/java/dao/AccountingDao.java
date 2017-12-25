package dao;

import com.google.inject.Inject;
import customInjections.ConnectionProvider;
import customInjections.InjectConnection;
import domain.Accounting;
import lombok.extern.log4j.Log4j2;
import service.MyException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Log4j2
public class AccountingDao {
    private final ConnectionProvider<Connection> connection;

    @Inject
    public AccountingDao(@InjectConnection ConnectionProvider<Connection> connection) {
        this.connection = connection;
    }

    public void setDataToTableAccounting(Accounting accounting) throws IOException, MyException {
        try (PreparedStatement prsmt = connection.get().prepareStatement(
                "INSERT INTO ACCOUNTING (LOGIN," +
                        "ROLE,PATH,START_DATE, END_DATE, VOLUME) VALUES (?,?,?,?,?,?)")) {
            prsmt.setString(1, accounting.getLogin());
            prsmt.setString(2, accounting.getRole());
            prsmt.setString(3, accounting.getPath());
            prsmt.setString(4, accounting.getDateStart());
            prsmt.setString(5, accounting.getDateEnd());
            prsmt.setLong(6, Long.parseLong(accounting.getVolume()));
            prsmt.executeUpdate();
            log.debug("data successfully adding");
        } catch (SQLException e) {
            log.error("request failed", e);
        }
    }

    public ArrayList<Accounting> findAllActivities() throws IOException, MyException {
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM ACCOUNTING")) {
            ArrayList<Accounting> activities = new ArrayList<>();
            try (ResultSet rs = prsmt.executeQuery()) {
                while (rs.next()) {
                    activities.add(new Accounting(
                            rs.getLong("ID"),
                            rs.getString("LOGIN"),
                            rs.getString("ROLE"),
                            rs.getString("PATH"),
                            rs.getString("START_DATE"),
                            rs.getString("END_DATE"),
                            rs.getString("VOLUME")));
                }
                return activities;
            }
        } catch (SQLException e) {
            log.error("Database error", e);
            throw new MyException("Database error", e);
        }
    }

    public Accounting findActivityById(long id) throws MyException, IOException {
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM ACCOUNTING WHERE ID = ?")) {
            prsmt.setLong(1, id);
            try (ResultSet rs = prsmt.executeQuery()) {
                while (rs.next()) {
                    return new Accounting(
                            rs.getLong("ID"),
                            rs.getString("LOGIN"),
                            rs.getString("ROLE"),
                            rs.getString("PATH"),
                            rs.getString("START_DATE"),
                            rs.getString("END_DATE"),
                            rs.getString("VOLUME"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new MyException("Database error", e);
        }
    }

    public Accounting findActivityByAuthorityId(long id) throws MyException, IOException {
        try (PreparedStatement prsmt = connection.get().prepareStatement("SELECT * FROM ACCOUNTING" +
                " WHERE RESOURCE_USERS_ROLES_ID = ?")) {
            prsmt.setLong(1, id);
            try (ResultSet rs = prsmt.executeQuery()) {
                while (rs.next()) {
                    return new Accounting(
                            rs.getLong("ID"),
                            rs.getString("LOGIN"),
                            rs.getString("ROLE"),
                            rs.getString("PATH"),
                            rs.getString("START_DATE"),
                            rs.getString("END_DATE"),
                            rs.getString("VOLUME"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new MyException("Database error", e);
        }
    }
}
