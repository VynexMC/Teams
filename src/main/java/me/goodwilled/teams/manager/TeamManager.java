package me.goodwilled.teams.manager;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.storage.Storage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TeamManager {
    private final Map<UUID, Team> teams = new HashMap<>();

    private final Storage storage;

    public TeamManager(Storage storage) {
        this.storage = storage;
    }

    public void shutdown() {
        this.teams.clear();
    }

    public void loadAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.loadUser(player.getUniqueId());
        }
    }

    public Team load(UUID uuid) {
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("Do not execute this method on the main server thread!");
        }
        return this.loadUser(uuid);
    }

    /*
     * This method stays private, Tyler... If you want to load a user's data, call the above method from a separate thread.
     */
    private Team loadUser(UUID uuid) {
        return this.teams.computeIfAbsent(uuid, id ->
                this.storage.getTeam(id).orElse(Team.CITIZEN)
        );
    }

    public void setTeam(UUID uuid, Team team, Consumer<Team> consumer) {
        this.updateTeam(uuid, team)
                .thenAcceptAsync(v -> this.teams.put(uuid, team))
                .thenAccept(v -> consumer.accept(team));
    }

    public void unload(UUID uuid) {
        this.teams.remove(uuid);
    }

    public Team getTeam(UUID uuid) {
        return this.teams.get(uuid);
    }

    private CompletableFuture<Void> updateTeam(UUID uuid, Team team) {
        return CompletableFuture.runAsync(() -> this.storage.setTeam(uuid, team));
    }
}
