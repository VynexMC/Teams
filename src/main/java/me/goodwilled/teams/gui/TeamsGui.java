package me.goodwilled.teams.gui;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.utils.ColourUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TeamsGui {
    // Don't ever do this, it's horrible. I'm just being lazy. I'll fix it eventually... Yeah...
    private static final TeamsPlugin plugin = TeamsPlugin.getPlugin(TeamsPlugin.class);

    public static final String TITLE = "Teams";

    private static final ItemStack LEAVE_TEAM_ICON = new ItemStack(Material.BARRIER);

    static {
        final ItemMeta leaveTeamIconMeta = LEAVE_TEAM_ICON.getItemMeta();
        leaveTeamIconMeta.setDisplayName(ColourUtils.colour("&cLeave Team"));
        leaveTeamIconMeta.setLore(Collections.singletonList(ColourUtils.colour("&fClick here to leave your current team.")));
        LEAVE_TEAM_ICON.setItemMeta(leaveTeamIconMeta);
    }

    public static Inventory forPlayer(Player player) {
        final Team currentTeam = plugin.getTeamManager().getTeam(player.getUniqueId());

        int rows = 3;
        if (currentTeam != Team.CITIZEN) {
            rows += 1;
        }

        final Inventory inventory = Bukkit.createInventory(null, rows * 9, TITLE);

        int slot = 10;
        for (Team team : Team.values()) {
            if (team == Team.CITIZEN) {
                continue;
            }

            final ItemStack icon = new ItemStack(team.getIcon(), 1);
            final ItemMeta meta = icon.getItemMeta();

            if (meta == null) {
                continue;
            }

            final List<String> description = Arrays.stream(team.getDescription()).map(ColourUtils::colour).collect(Collectors.toList());

            description.add(0, ""); // Blank line.
            description.add(""); // Yet another blank line.

            if (team == currentTeam) {
                meta.setDisplayName(team.getPrefix());
                description.add(ChatColor.RED + "You are already on this team.");
            } else {
                meta.setDisplayName(team.getPrefix());
                description.add(ChatColor.RED + "Fee: " + ChatColor.DARK_GREEN + "$" + ChatColor.GREEN + plugin.getConfig().getDouble("team-change-fee"));
            }

            meta.setLore(description);
            icon.setItemMeta(meta);

            inventory.setItem(slot, icon);
            slot += 2;
        }

        if (currentTeam != Team.CITIZEN) {
            inventory.setItem(31, LEAVE_TEAM_ICON);
        }

        return inventory;
    }
}
