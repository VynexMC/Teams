package me.goodwilled.teams.listeners;

import me.goodwilled.teams.TeamsPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MiscListener implements Listener {
    private static final Material[] SWORD_MATERIALS = {
            Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD,
            Material.GOLDEN_SWORD, Material.GOLDEN_SWORD, Material.NETHERITE_SWORD
    };

    private final TeamsPlugin teamsPlugin;

    public MiscListener(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }

    // me trying to make a farm event check
    public void farmEvent(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.DIRT) {
                if (event.getPlayer().getItemOnCursor().getType() == Material.WHEAT_SEEDS ||
                        event.getPlayer().getItemOnCursor().getType() == Material.MELON_SEEDS||
                        event.getPlayer().getItemOnCursor().getType() == Material.BEETROOT_SEEDS) {
                    // do stuff with teams in here
                }
            }
        }
    }

}
