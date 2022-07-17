package me.goodwilled.teams.storage;

import me.goodwilled.teams.Team;

import java.util.Optional;
import java.util.UUID;

public interface Storage {
    /**
     * Initialize the storage implementation.
     */
    void init();

    /**
     * Shuts down the storage implementation.
     */
    void shutdown();

    /**
     * Set a player's team.
     *
     * @param uuid - The player's uuid.
     * @param team - The player's new team.
     */
    void setTeam(UUID uuid, Team team);

    /**
     * Get a player's team.
     *
     * @param uuid - The uuid of the player.
     * @return An optional team.
     */
    Optional<Team> getTeam(UUID uuid);
}
