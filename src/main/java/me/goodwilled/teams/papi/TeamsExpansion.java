package me.goodwilled.teams.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.goodwilled.teams.TeamsPlugin;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TeamsExpansion extends PlaceholderExpansion {
    private final TeamsPlugin plugin;

    public TeamsExpansion(TeamsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return this.plugin.getDescription().getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return "SavageAvocado";
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player.isOnline()) {
            if (params.equalsIgnoreCase("prefix")) {
                return this.plugin.getTeamManager().getTeam(player.getUniqueId()).getPrefix();
            }

            if (params.equalsIgnoreCase("prefix_short")) {
                return this.plugin.getTeamManager().getTeam(player.getUniqueId()).getPrefixShort();
            }
        }
        return null;
    }
}
