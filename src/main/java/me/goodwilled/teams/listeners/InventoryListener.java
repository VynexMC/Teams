package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {
    private final TeamsPlugin teamsPlugin;

    public InventoryListener(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(TeamsPlugin.TEAMS_GUI_TITLE)) {
            e.setCancelled(true);

            Team team = null;
            switch (e.getSlot()) {
                case 10:
                    team = Team.KNIGHT;
                    break;
                case 12:
                    team = Team.MAGE;
                    p.setWalkSpeed(0.3f);
                    p.setFlySpeed(0.3f);
                    break;
                case 14:
                    team = Team.ASSASSIN;
                    break;
                case 16:
                    team = Team.VIKING;
                    break;
            }

            if (team == null) {
                return;
            }

            final Team currentTeam = this.teamsPlugin.getTeamManager().getTeam(p.getUniqueId());

            // They're not the default team, so we know to charge them for a team change.
            if (currentTeam != Team.CITIZEN) {
                this.teamsPlugin.getEconomy().ifPresent(economy ->
                        economy.withdrawPlayer(p, this.teamsPlugin.getConfig().getDouble("team-change-fee"))
                );
            }

            this.teamsPlugin.getTeamManager().setTeam(p.getUniqueId(), team, newTeam -> {
                p.sendMessage(TeamsPlugin.PREFIX + ChatColor.AQUA + "Set your team to " + ChatColor.GREEN + newTeam.name() + ".");
            });

            p.closeInventory();
        }
    }

    @EventHandler
    public void invClose(InventoryCloseEvent e) {
 /*       Player p = (Player) e.getPlayer();
        if (e.getView().getTitle().equals(TeamsPlugin.TEAMS_GUI_TITLE)) {
            if (!teamsPlugin.getteamsConfig().contains(p.getUniqueId().toString())) {
                teamsPlugin.getServer().getScheduler().runTaskLater(teamsPlugin, () ->
                                p.openInventory(TeamsPlugin.createteamsGui()),
                        1L);
            }
        }*/
    }
}
