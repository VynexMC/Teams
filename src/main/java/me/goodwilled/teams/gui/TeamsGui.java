package me.goodwilled.teams.gui;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import me.goodwilled.teams.utils.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class TeamsGui {
    public static final String TITLE = "Teams";

    public static Inventory forPlayer(Player player) {
        final Inventory inventory = Bukkit.createInventory(null, 27, TITLE);

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

            final Team currentTeam = TeamsPlugin.getPlugin(TeamsPlugin.class).getTeamManager().getTeam(player.getUniqueId());

            if (team == currentTeam) {
                meta.setDisplayName(team.getPrefix());
                meta.setLore(Collections.singletonList(ColourUtils.colour("&cYou are already on this team.")));
            } else {
                meta.setDisplayName(team.getPrefix());
                meta.setLore(Arrays.asList(team.getDescription()));
            }


            icon.setItemMeta(meta);

            inventory.setItem(slot, icon);
            slot += 2;
        }

        return inventory;
    }

    /*public static Inventory createTeamsGui() {
        ItemStack knight = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemStack mage = new ItemStack(Material.ENDER_PEARL, 1);
        ItemStack tamer = new ItemStack(Material.COW_SPAWN_EGG, 1);
        ItemStack archer = new ItemStack(Material.BOW, 1);
        ItemMeta aMeta = knight.getItemMeta();
        ItemMeta nMeta = mage.getItemMeta();
        ItemMeta wMeta = tamer.getItemMeta();
        ItemMeta vMeta = archer.getItemMeta();
        aMeta.setDisplayName(ChatColor.BLUE + "KNIGHT");
        nMeta.setDisplayName(ChatColor.DARK_GREEN + "MAGE");
        wMeta.setDisplayName(ChatColor.DARK_RED + "TAMER");
        vMeta.setDisplayName(ChatColor.GOLD + "ARCHER");
        aMeta.setLore(Collections.singletonList("Ability to craft swords."));
        nMeta.setLore(Collections.singletonList("Magic."));
        wMeta.setLore(Collections.singletonList("Shoot like a Skeleton."));
        vMeta.setLore(Collections.singletonList("Animal army!"));
        knight.setItemMeta(aMeta);
        archer.setItemMeta(vMeta);
        mage.setItemMeta(nMeta);
        tamer.setItemMeta(wMeta);
        inventory.setItem(10, knight);
        inventory.setItem(12, mage);
        inventory.setItem(14, tamer);
        inventory.setItem(16, archer);
        return inventory;
    }*/
}
