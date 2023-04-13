package me.goodwilled.teams.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.utils.ColourUtils;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;

public class ChatListener implements Listener {
    private final TeamsPlugin teamsPlugin;

    public ChatListener(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        Team team = this.teamsPlugin.getTeamManager().getTeam(player.getUniqueId());
        // Player's display name
        final String prefix = this.getPrefix(event.getPlayer()).orElse("&f");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(team.getPrefix() + " " + prefix + player.getDisplayName() + "&8 \u00BB &r" + event.getMessage()));
        }
        event.getRecipients().clear();
    }

    private String getGroup(Player player) {
        if (this.teamsPlugin.getLuckPerms().isEmpty()) {
            return "N/A"; // LuckPerms API isn't present. Return placeholder value.
        }
        final User user = this.teamsPlugin.getLuckPerms().get().getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            return "Unknown"; // No user found for the player. Return placeholder value.
        }
        final Group group = this.teamsPlugin.getLuckPerms().get().getGroupManager().getGroup(user.getPrimaryGroup());
        if (group == null) {
            return "Unknown"; // Group doesn't exist... You get the idea by now.
        }
        return ColourUtils.colour(group.getFriendlyName());
    }


    private Optional<String> getPrefix(Player player) {
        if (this.teamsPlugin.getLuckPerms().isEmpty()) {
            return Optional.empty();
        }
        final User user = this.teamsPlugin.getLuckPerms().get().getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(user.getCachedData().getMetaData().getPrefix());
    }

}
