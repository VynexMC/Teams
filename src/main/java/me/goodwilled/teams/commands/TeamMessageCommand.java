package me.goodwilled.teams.commands;

import com.sun.tools.javac.Main;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.utils.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TeamMessageCommand implements CommandExecutor {
    final TeamsPlugin teamsPlugin;
    public TeamMessageCommand(TeamsPlugin teamsPlugin){
        this.teamsPlugin = teamsPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player = (Player) sender;
        if (!(sender instanceof Player)) {
            return true;
        }
        if(args.length == 0) {
            return false;
        }
        if(args.length >= 1) {
            String msg = "";
            //combine the arguments the player typed
            for (int i = 1; i < args.length; i++){
                String arg = (args[i] + " ");
                msg = (msg + arg);
            }
            for(Player online : Bukkit.getOnlinePlayers()) {
                if(this.teamsPlugin.getTeamManager().getTeam(online.getUniqueId()) ==
                this.teamsPlugin.getTeamManager().getTeam(player.getUniqueId())){
                    online.sendMessage(ColourUtils.colour(this.teamsPlugin.getConfig().getString("prefix")
                                     ));
                }
            }
        }

        return true;
    }
}
