package me.goodwilled.teams.listeners;

import me.goodwilled.teams.TeamsPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class MiscListener implements Listener {
    private static final Material[] SWORD_MATERIALS = {
            Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD,
            Material.GOLDEN_SWORD, Material.GOLDEN_SWORD, Material.NETHERITE_SWORD
    };

    private final TeamsPlugin teamsPlugin;

    public MiscListener(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }


    // What is this? It seemingly adds no functionality to this plugin what-so-ever.
    // Are Mages going to be able to throw fireballs in the future?
    // You should definitely check who threw the fireball in that case.
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FIREBALL) {
            event.setCancelled(true);
        }
    }

    private boolean isSword(Material material) {
        for (Material swordMaterial : SWORD_MATERIALS) {
            if (material == swordMaterial) {
                return true;
            }
        }
        return false;
    }
}
