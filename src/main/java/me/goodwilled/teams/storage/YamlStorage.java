package me.goodwilled.teams.storage;

import me.goodwilled.teams.Team;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class YamlStorage implements Storage {
    private final Path path;

    private FileConfiguration configuration;

    public YamlStorage(Path path) {
        this.path = path;
    }

    @Override
    public void init() {
        if (Files.notExists(this.path)) {
            try {
                Files.createFile(this.path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (final Reader reader = Files.newBufferedReader(this.path)) {
            this.configuration = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        try {
            this.configuration.save(this.path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTeam(UUID uuid, Team team) {
        this.configuration.set(uuid.toString(), team.name());
        try {
            this.configuration.save(this.path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Team> getTeam(UUID uuid) {
        final String teamName = this.configuration.getString(uuid.toString());
        if (teamName == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(Team.valueOf(teamName.toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException ignored) {
            // The player's team was removed from the plugin at some point.
        }
        return Optional.empty();
    }
}
