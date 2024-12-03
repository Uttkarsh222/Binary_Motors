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


    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


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
