package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.CraftItemEvent;

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
