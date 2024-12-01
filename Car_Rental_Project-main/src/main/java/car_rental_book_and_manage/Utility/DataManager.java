package car_rental_book_and_manage.Utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Utility class for managing database connections using HikariCP. Source:
 * https://www.baeldung.com/hikaricp
 */
public class DataManager {

    private static final DataSource dataSource;

    // Static block to initialize the data source
    static {
        HikariConfig config = new HikariConfig();
        String jdbcUrl = ConfigManager.getProperty("db.url");
        String username = ConfigManager.getProperty("db.user");
        String password = ConfigManager.getProperty("db.password");

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    /**
     * Gets a connection from the data source.
     *
     * @return a connection to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the given connection if it is not null.
     *
     * @param connection the connection to close
     */
    public static void disconnect(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
