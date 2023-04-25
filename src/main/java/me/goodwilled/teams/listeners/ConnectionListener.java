package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.utils.ColourUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final TeamsPlugin teamsPlugin;

    public ConnectionListener(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }

    @EventHandler
    public void on(AsyncPlayerPreLoginEvent event) {
        this.teamsPlugin.getTeamManager().load(event.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final Team team = this.teamsPlugin.getTeamManager().getTeam(player.getUniqueId());

        if (team == Team.CITIZEN) {
            player.sendMessage(ColourUtils.colour("&aLooks like you haven't selected a Team yet. Do &b/teams &a to do so!"));
        } else {
            team.applyJoinPerks(player);
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        this.teamsPlugin.getTeamManager().unload(event.getPlayer().getUniqueId());
    }
}
