package me.goodwilled.teams.gui;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.utils.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class TeamsGui {
    public static final String TITLE = "Teams";

    private static final ItemStack LEAVE_TEAM_ICON = new ItemStack(Material.BARRIER);

    static {
        final ItemMeta leaveTeamIconMeta = LEAVE_TEAM_ICON.getItemMeta();
        leaveTeamIconMeta.setDisplayName(ColourUtils.colour("&cLeave Team"));
        leaveTeamIconMeta.setLore(Collections.singletonList(ColourUtils.colour("&fClick here to leave your current team.")));
        LEAVE_TEAM_ICON.setItemMeta(leaveTeamIconMeta);
    }

    public static Inventory forPlayer(Player player) {
        // Don't ever do this, it's horrible. I'm just being lazy.
        final Team currentTeam = TeamsPlugin.getPlugin(TeamsPlugin.class).getTeamManager().getTeam(player.getUniqueId());

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

            if (team == currentTeam) {
                meta.setDisplayName(team.getPrefix());
                meta.setLore(Collections.singletonList(ColourUtils.colour("&cYou are already on this team.")));
            } else {
                meta.setDisplayName(team.getPrefix());
                meta.setLore(Arrays.stream(team.getDescription()).map(ColourUtils::colour).toList());
            }


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
