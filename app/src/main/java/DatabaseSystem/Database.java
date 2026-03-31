package DatabaseSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConsoleSystem.Console;
import ConsoleSystem.ConsoleColors;

public class Database {

    protected static String databaseName = "avidadatabase";
    protected static String username = "root";
    protected static String password = "AvidaDatabase101";
    protected static String ip = "localhost";
    protected static String port = "3306";

    private static Connection connection;

    public static void openConnection() {
        Console.line().out("CONNECTING TO DATABASE '" + databaseName + "'", ConsoleColors.GREEN);
        try {
            Console.out("Connecting: " + "jdbc:mysql://" + ip + ":" + port + "/" + databaseName);
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + databaseName, username,
                    password);
            Console.out("Connection success");
        } catch (SQLException e) {
            Console.errorOut("Connection failed", e);
        }
    }

    public static void closeConnection() {
        if (connection == null)
            return;
        Console.line().out("CLOSING CONNECTION", ConsoleColors.GREEN);
        try {
            connection.close();
            Console.out("Connection closed");
        } catch (SQLException e) {
            Console.errorOut("Connection closing error", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static boolean isOpen() {
        if (connection == null)
            return false;
        try {
            if (connection.isClosed())
                return false;
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

// Secure Methods for INSERT, UPDATE, DELETE =================================================================
    
    public static void executePrepared(String query, Object... parameters) throws SQLException {
        if (connection == null) throw new SQLException("Database offline");
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            statement.executeUpdate();
        }
    }

// Secure Methods for SELECT queries =========================================================================

    public interface PreparedStatementResult {
        void execute(ResultSet result) throws SQLException;
    }

    public static void executePreparedQuery(String query, PreparedStatementResult resultExecute, Object... parameters) throws SQLException {
        if (connection == null) throw new SQLException("Database offline");
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            try (ResultSet result = statement.executeQuery()) {
                resultExecute.execute(result);
            }
        }
    }
}