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
            final ComponentBuilder builder = new ComponentBuilder();

            builder.append(ColourUtils.colour("&aLooks like you haven't selected a class! Click me to do so!"))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click me to select a class.")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teams"));

            player.spigot().sendMessage(builder.create());
        } else {
            switch (team) {
                case KNIGHT:
                case TAMER:
                    break;
                case MAGE:
                    player.setWalkSpeed(0.3f);
                    player.setFlySpeed(0.3f);
                    break;
                case ARCHER:
            }

            for (Player online : Bukkit.getOnlinePlayers()) {
                final Team onlinePlayerTeam = this.teamsPlugin.getTeamManager().getTeam(online.getUniqueId());
                if (onlinePlayerTeam == Team.CITIZEN || onlinePlayerTeam == team) {
                    continue;
                }
                online.sendTitle(ChatColor.DARK_RED + "An enemy emerges...", ChatColor.GOLD + "Defend what's yours.", 25, 60, 25);
            }
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        this.teamsPlugin.getTeamManager().unload(event.getPlayer().getUniqueId());
    }
}
