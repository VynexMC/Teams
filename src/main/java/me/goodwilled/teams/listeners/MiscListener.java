package me.goodwilled.teams.listeners;

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

    @EventHandler
    public void entityTame(EntityTameEvent e) {
        Player p = (Player) e.getOwner();
        if (!(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString()) == null)) {
            if (!teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString()).equalsIgnoreCase("TAMER") || !p.hasPermission("teams.bypass.tame")) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.DARK_RED + "You must be a Tamer to breed/tame this animal.");
            }
        }
    }

    @EventHandler
    public void entityBreed(EntityBreedEvent e) {
        final Player p = (Player) e.getBreeder();

        if (p.hasPermission("teams.bypass.tame")) {
            return;
        }

        final String teamName = this.teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString());

        if (teamName == null || !teamName.equalsIgnoreCase("TAMER")) {
            p.sendMessage(ChatColor.DARK_RED + "You must be a Tamer to breed/tame this animal.");
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onCraft(CraftItemEvent e) {
        Player p = (Player) e.getWhoClicked();
        final Material result = e.getRecipe().getResult().getType();
        if (!(p.hasPermission("teams.bypass.craft") || teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString()).equalsIgnoreCase("KNIGHT"))) {
            if (this.isSword(result)) {
                p.sendMessage(ChatColor.RED + "&4You must be a Knight to craft this item.");
                e.setCancelled(true);
            }
        }

        if (!(p.hasPermission("teams.bypass.craft") || teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString()).equalsIgnoreCase("ARCHER"))) {
            if (result == Material.BOW || result == Material.CROSSBOW) {
                p.sendMessage(ChatColor.RED + "You must be an Archer to craft this item.");
                e.setCancelled(true);
            }
        }
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
