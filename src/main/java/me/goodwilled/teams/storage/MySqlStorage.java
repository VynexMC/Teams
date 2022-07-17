package me.goodwilled.teams.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.goodwilled.teams.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class MySqlStorage implements Storage {
    private static final String DATABASE_URL = "jdbc:mysql://%s:%d/%s";
    private static final String POOL_NAME = "teams-hikari";

    private static final String CREATE_TEAM_DATA_TABLE = "CREATE TABLE IF NOT EXISTS team_data (id VARCHAR(36) PRIMARY KEY NOT NULL, team TEXT);";

    private static final String INSERT_OR_UPDATE_TEAM = "INSERT INTO team_data (id, team) VALUES (?, ?) ON DUPLICATE KEY UPDATE team = ?;";

    private static final String SELECT_TEAM = "SELECT team FROM team_data WHERE id = ?;";

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    private HikariDataSource source;

    public MySqlStorage(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Override
    public void init() {
        final HikariConfig config = new HikariConfig();

        config.setPoolName(POOL_NAME);
        config.setJdbcUrl(String.format(DATABASE_URL,
                this.host,
                this.port,
                this.database
        ));
        config.setUsername(this.username);
        config.setPassword(this.password);

        this.source = new HikariDataSource(config);

        this.createTables();
    }

    private void createTables() {
        try (final Connection connection = this.getConnection()) {
            try (final PreparedStatement statement = connection.prepareStatement(CREATE_TEAM_DATA_TABLE)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        if (this.source == null) {
            return;
        }
        this.source.close();
    }

    @Override
    public void setTeam(UUID uuid, Team team) {
        try (final Connection connection = this.getConnection()) {
            try (final PreparedStatement statement = connection.prepareStatement(INSERT_OR_UPDATE_TEAM)) {
                statement.setString(1, uuid.toString());
                statement.setString(2, team.name());
                statement.setString(3, team.name());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Team> getTeam(UUID uuid) {
        try (final Connection connection = this.getConnection()) {
            try (final PreparedStatement statement = connection.prepareStatement(SELECT_TEAM)) {
                statement.setString(1, uuid.toString());
                try (final ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        return Optional.empty();
                    }
                    return Optional.of(Team.valueOf(result.getString("team")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        if (this.source == null) {
            throw new IllegalStateException("HikariDataSource has not been initialized!");
        }
        try {
            return this.source.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
