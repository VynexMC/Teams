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
                    team = Team.TAMER;
                    break;
                case 16:
                    team = Team.ARCHER;
                    break;
            }

            if (team == null) {
                return;
            }

            this.teamsPlugin.getTeamManager().setTeam(p.getUniqueId(), team, newTeam -> {
                p.sendMessage(TeamsPlugin.PREFIX + ChatColor.AQUA + "Set your team to " + ChatColor.GOLD + newTeam.name() + ".");

                for (Player online : Bukkit.getOnlinePlayers()) {
                    final Team onlinePlayerTeam = this.teamsPlugin.getTeamManager().getTeam(online.getUniqueId());
                    if (onlinePlayerTeam != newTeam) {
                        online.sendTitle(ChatColor.DARK_RED + "An enemy emerges...", ChatColor.GOLD + "Defend what's yours.", 25, 60, 25);
                    }
                }
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
