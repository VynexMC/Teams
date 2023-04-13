package me.goodwilled.teams.commands;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;


public class TeamMessageCommand implements CommandExecutor {
    final TeamsPlugin teamsPlugin;

    public TeamMessageCommand(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {
            return false;
        }

        final StringBuilder builder = new StringBuilder();

        for (String word : args) {
            builder.append(word).append(' ');
        }

        final Team team = this.teamsPlugin.getTeamManager().getTeam(player.getUniqueId());

        for (UUID uuid : this.teamsPlugin.getTeamManager().getPlayersOnTeam(team)) {
            final Player teammate = Bukkit.getPlayer(uuid);
            if (teammate == null) {
                continue;
            }
            teammate.sendMessage(TeamsPlugin.PREFIX + builder.toString().trim());
        }

        return true;
    }
}
