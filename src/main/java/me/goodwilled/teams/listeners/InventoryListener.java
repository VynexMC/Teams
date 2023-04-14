package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.gui.TeamsGui;
import me.goodwilled.teams.utils.ColourUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {
    private final TeamsPlugin teamsPlugin;

    public InventoryListener(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(TeamsGui.TITLE)) {
            e.setCancelled(true);

            Team team = null;
            switch (e.getSlot()) {
                case 10 -> team = Team.KNIGHT;
                case 12 -> {
                    team = Team.MAGE;
                    player.setWalkSpeed(0.3f);
                    player.setFlySpeed(0.3f);
                }
                case 14 -> team = Team.ASSASSIN;
                case 16 -> team = Team.VIKING;
                case 31 -> {
                    this.teamsPlugin.getTeamManager().setTeam(player.getUniqueId(), Team.CITIZEN, ignored ->
                            player.sendMessage(ColourUtils.colour(TeamsPlugin.PREFIX + "&cYou have left your team."))
                    );
                    player.closeInventory();
                    return;
                }
            }

            if (team == null) {
                return;
            }

            final double teamChangeFee = this.teamsPlugin.getConfig().getDouble("team-change-fee");

            if (!this.teamsPlugin.getTeamManager().isFirstTeamChange(player.getUniqueId())) {
                this.teamsPlugin.getEconomy().ifPresent(economy -> {
                            economy.withdrawPlayer(player, teamChangeFee);
                            player.sendMessage(TeamsPlugin.PREFIX + ChatColor.RED + "-" + ChatColor.DARK_GREEN + "$" + ChatColor.GREEN + teamChangeFee);
                        }
                );
            }

            final Team currentTeam = this.teamsPlugin.getTeamManager().getTeam(player.getUniqueId());

            if (team == currentTeam) {
                player.sendMessage(TeamsPlugin.PREFIX + ChatColor.RED + "You are already on the " + currentTeam.getPrefix() + ChatColor.RED + " team.");
            } else {
                this.teamsPlugin.getTeamManager().setTeam(player.getUniqueId(), team, newTeam ->
                        player.sendMessage(TeamsPlugin.PREFIX + ChatColor.AQUA + "Set your team to " + ChatColor.GREEN + newTeam.getPrefix())
                );
            }

            player.closeInventory();
        }
    }
}
