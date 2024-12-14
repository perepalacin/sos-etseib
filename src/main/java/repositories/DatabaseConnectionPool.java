package repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionPool {
    private static HikariDataSource dataSource;

    static {
        Properties props = new Properties();
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            props.load(new FileInputStream("/Users/perepalacin/Documents/pere-repos/files-system/sos-etseib/env.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read database configuration", e);
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/sos-etseib");
        config.setUsername(props.getProperty("DB_USERNAME").trim());
        config.setPassword(props.getProperty("DB_PASSWORD").trim());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}