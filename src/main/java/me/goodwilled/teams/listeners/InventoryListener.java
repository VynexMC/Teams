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
        if (e.getView().getTitle().equals("Teams")) {
            e.setCancelled(true);
            switch (e.getSlot()) {
                case 10:
                    p.sendMessage(TeamsPlugin.PREFIX + ChatColor.AQUA + "Set your team to " + ChatColor.BLUE + "KNIGHT.");
                    //main.getServer().getScheduler().runTaskLaterAsynchronously(main, () -> p.setPlayerListName(ChatColor.BLUE + "KNIGHT: " + ChatColor.WHITE + p.getName()), 1L);
                    teamsPlugin.getteamsConfig().set(p.getUniqueId().toString(), Team.KNIGHT.toString());
                    teamsPlugin.saveteamsConfig();
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        Team oTeam = Team.valueOf(teamsPlugin.getteamsConfig().getString(online.getUniqueId().toString()));
                        Team teamName = Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString()));
                        if (!oTeam.equals(teamName)) {
                            online.sendTitle(ChatColor.DARK_RED + "An enemy emerges...", ChatColor.GOLD + "Defend what's yours.", 25, 60, 25);
                        }
                    }
                    p.closeInventory();
                    break;
                case 12:
                    p.sendMessage(TeamsPlugin.PREFIX + ChatColor.AQUA + "Set your team to " + ChatColor.DARK_GREEN + "MAGE.");
                    //  main.getServer().getScheduler().runTaskLaterAsynchronously(main, () -> p.setPlayerListName(ChatColor.DARK_GREEN + "MAGE: " + ChatColor.WHITE + p.getName()), 1L);
                    teamsPlugin.getteamsConfig().set(p.getUniqueId().toString(), Team.MAGE.toString());
                    teamsPlugin.saveteamsConfig();
                    p.setWalkSpeed(0.3f);
                    p.setFlySpeed(0.3f);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        Team oTeam = Team.valueOf(teamsPlugin.getteamsConfig().getString(online.getUniqueId().toString()));
                        Team teamName = Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString()));
                        if (!oTeam.equals(teamName)) {
                            online.sendTitle(ChatColor.DARK_RED + "An enemy emerges...", ChatColor.GOLD + "Defend what's yours.", 25, 60, 25);
                        }
                    }
                    p.closeInventory();
                    break;
                case 14:
                    p.sendMessage(TeamsPlugin.PREFIX + ChatColor.AQUA + "Set your team to " + ChatColor.DARK_RED + "TAMER.");
                    // main.getServer().getScheduler().runTaskLaterAsynchronously(main, () -> p.setPlayerListName(ChatColor.DARK_RED + "TAMER: " + ChatColor.WHITE + p.getName()), 1L);
                    teamsPlugin.getteamsConfig().set(p.getUniqueId().toString(), Team.ARCHER.toString());
                    teamsPlugin.saveteamsConfig();
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        Team oTeam = Team.valueOf(teamsPlugin.getteamsConfig().getString(online.getUniqueId().toString()));
                        Team teamName = Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString()));
                        if (!oTeam.equals(teamName)) {
                            online.sendTitle(ChatColor.DARK_RED + "An enemy emerges...", ChatColor.GOLD + "Defend what's yours.", 25, 60, 25);
                        }
                    }
                    p.closeInventory();
                    break;
                case 16:
                    p.sendMessage(TeamsPlugin.PREFIX + ChatColor.AQUA + "Set your team to " + ChatColor.GOLD + "ARCHER.");
                    // main.getServer().getScheduler().runTaskLaterAsynchronously(main, () -> p.setPlayerListName(ChatColor.GOLD + "ARCHER: " + ChatColor.WHITE + p.getName()), 1L);
                    teamsPlugin.getteamsConfig().set(p.getUniqueId().toString(), Team.ARCHER.toString());
                    teamsPlugin.saveteamsConfig();
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        Team oTeam = Team.valueOf(teamsPlugin.getteamsConfig().getString(online.getUniqueId().toString()));
                        Team teamName = Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString()));
                        if (!oTeam.equals(teamName)) {
                            online.sendTitle(ChatColor.DARK_RED + "An enemy emerges...", ChatColor.GOLD + "Defend what's yours.", 25, 60, 25);
                        }
                    }
                    p.closeInventory();
                    break;

            }
        }
    }

    @EventHandler
    public void invClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getView().getTitle().equals("teams")) {
            if (!teamsPlugin.getteamsConfig().contains(p.getUniqueId().toString())) {
                teamsPlugin.getServer().getScheduler().runTaskLater(teamsPlugin, () ->
                                p.openInventory(TeamsPlugin.createteamsGui()),
                        1L);
            }
        }
    }
}
