package repositories;

import entities.UserDao;
import exceptions.InvalidCredentialsException;
import exceptions.UserNotFoundException;
import lombok.Generated;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UsersRepository {

    public UsersRepository() {
        createUsersTable();
        createSessionsTable();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), email VARCHAR(255) UNIQUE, password VARCHAR(255), activated BOOLEAN NOT NULL DEFAULT FALSE, created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";
        try (Connection connection = DatabaseConnectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.execute();
                System.out.println("Table 'users' created or already exists.");
        } catch (SQLException e) {
            System.out.println("Failed to initialize the users table");
            e.printStackTrace();
        }
    }

    public void createSessionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS sessions (session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(), email VARCHAR(255), expiration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";
        try (Connection connection = DatabaseConnectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            System.out.println("Table 'sessions' created or already exists.");
        } catch (SQLException e) {
            System.out.println("Failed to initialize the sessions table");
            e.printStackTrace();
        }
    }

    public UserDao findUserByEmail(String email) throws PSQLException, SQLException {
        String sql = "SELECT * FROM users WHERE email = ?;";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new UserDao(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("activated")
            );
        } else {
            return null;
        }
    }

    public UserDao createUser(String email, String password) throws PSQLException, SQLException {
        String sql = "INSERT INTO users (email, password) VALUES (?, ?) RETURNING id, email, password;";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new UserDao(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("email"),
                    rs.getString("password"),
                    false
            );
        }

        return null;
    }

    public void activateUser(UUID id) throws SQLException {
        String sql = "UPDATE users SET activated = ? WHERE id = ?;";

        Connection connection = DatabaseConnectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        System.out.println(id);
        ps.setBoolean(1, true);
        ps.setObject(2, id);
        ps.executeUpdate();
    }



}
