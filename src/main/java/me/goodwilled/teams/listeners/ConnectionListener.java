package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.utils.ColourUtils;
import org.bukkit.Bukkit;
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
            Bukkit.broadcastMessage(ColourUtils.colour("&a" + player.getName() + " &7&ojoined the game."));
        } else {
            switch (team) {
                case KNIGHT -> {
                    Bukkit.getServer().broadcastMessage(ColourUtils.colour("&5&oBe warned... a &3Knight &5&ohas joined the game."));
                    Bukkit.getServer().broadcastMessage(ColourUtils.colour("&5&oGreetings, &a" + player.getName() + "&5&o."));
                }
                case ASSASSIN -> {
                    Bukkit.getServer().broadcastMessage(ColourUtils.colour("&6&oWatch your back... because an &4Assassin &6&ohas joined the game."));
                    Bukkit.getServer().broadcastMessage(ColourUtils.colour("&6&oNice to see you, &a" + player.getName() + "&6&o."));
                }
                case MAGE -> {
                    Bukkit.getServer().broadcastMessage(ColourUtils.colour("&3&oA strong breeze blows through the world... because a &2Mage &3&ohas joined the game."));
                    Bukkit.getServer().broadcastMessage(ColourUtils.colour("&3&oHello, &a" + player.getName() + "&3&o."));
                }
                case VIKING -> {
                    Bukkit.getServer().broadcastMessage(ColourUtils.colour("&c&oCarry a shield with you... because a &6Viking &c&ohas joined the game."));
                    Bukkit.getServer().broadcastMessage(ColourUtils.colour("&c&oWe missed you, &a" + player.getName() + "&c&o."));
                }
            }

            team.applyJoinPerks(player);
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        this.teamsPlugin.getTeamManager().unload(event.getPlayer().getUniqueId());
        Bukkit.getServer().broadcastMessage(ColourUtils.colour("&c" + player.getName() + " &7&oleft the game."));
    }

}
