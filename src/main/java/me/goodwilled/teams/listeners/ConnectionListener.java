package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.manager.TeamManager;
import me.goodwilled.teams.utils.ColourUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Locale;

import static org.bukkit.scoreboard.DisplaySlot.SIDEBAR;

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
            switch (team) {
                case KNIGHT:
                    Bukkit.getServer().broadcastMessage("&5&oBe warned... a &9Knight &5&ohas joined the game.");
                    Bukkit.getServer().broadcastMessage("&5&oGreetings, &a" + player.getName() + "&5&o.");
                    break;
                case ASSASSIN:
                    Bukkit.getServer().broadcastMessage("&6&oWatch your back... because an &4Assassin &6&ohas joined the game.");
                    Bukkit.getServer().broadcastMessage("&6&oNice to see you, &a" + player.getName() + "&6&o.");
                    break;
                case MAGE:
                    Bukkit.getServer().broadcastMessage("&3&oA strong breeze blows through the world... because a &2Mage &3&ohas joined the game.");
                    Bukkit.getServer().broadcastMessage("&3&oHello, &a" + player.getName() + "&3&o.");
                    player.setWalkSpeed(0.3f);
                    player.setFlySpeed(0.3f);
                    break;
                case VIKING:
                    Bukkit.getServer().broadcastMessage("&c&oCarry a shield with you... because a &6Viking &c&ohas joined the game.");
                    Bukkit.getServer().broadcastMessage("&c&oWe missed you, &a" + player.getName() + "&c&o.");
                    break;
            }
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        final Team team = this.teamsPlugin.getTeamManager().getTeam(player.getUniqueId());
        this.teamsPlugin.getTeamManager().unload(event.getPlayer().getUniqueId());
        Bukkit.getServer().broadcastMessage("&c" + player.getName() + "&7&oleft the game.");
    }

}
