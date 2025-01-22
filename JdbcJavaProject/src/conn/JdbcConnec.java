package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnec {

    private static JdbcConnec jdConnec;
    private Connection connection;

    private final String url = "jdbc:mysql://localhost:3306/db";
    private final String user = "root";
    private final String pass = "Rudra@378";

    private JdbcConnec() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            throw e;
        }
    }

    public static JdbcConnec getInstance() throws SQLException {
        if (jdConnec == null) {
            synchronized (JdbcConnec.class) {
                if (jdConnec == null) {
                    jdConnec = new JdbcConnec();
                }
            }
        }
        return jdConnec;
    }

    public Connection getConn() throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is null. Check connection initialization.");
        }
        return connection;
    }
}