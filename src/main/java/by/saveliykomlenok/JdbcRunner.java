package by.saveliykomlenok;

import by.saveliykomlenok.utils.ConnectionManager;

import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        try (var connection = ConnectionManager.get()) {
            System.out.println(connection.getTransactionIsolation());
        }
    }
}