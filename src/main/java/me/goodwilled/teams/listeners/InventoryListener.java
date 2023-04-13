package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.gui.TeamsGui;
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
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(TeamsGui.TITLE)) {
            e.setCancelled(true);

            Team team = null;
            switch (e.getSlot()) {
                case 10 -> team = Team.KNIGHT;
                case 12 -> {
                    team = Team.MAGE;
                    p.setWalkSpeed(0.3f);
                    p.setFlySpeed(0.3f);
                }
                case 14 -> team = Team.ASSASSIN;
                case 16 -> team = Team.VIKING;
            }

            if (team == null) {
                return;
            }

            final double teamChangeFee = this.teamsPlugin.getConfig().getDouble("team-change-fee");

            // They're not the default team, so we know to charge them for a team change.
            if (this.teamsPlugin.getTeamManager().isFirstTeamChange(p.getUniqueId())) {
                this.teamsPlugin.getEconomy().ifPresent(economy ->
                        economy.withdrawPlayer(p, teamChangeFee)
                );
            }

            this.teamsPlugin.getTeamManager().setTeam(p.getUniqueId(), team, newTeam ->
                    p.sendMessage(TeamsPlugin.PREFIX + ChatColor.AQUA + "Set your team to " + ChatColor.GREEN + newTeam.getPrefix() + ".\n&c-$" + teamChangeFee)
            );

            p.closeInventory();
        }
    }
}
