package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamageListener implements Listener {
    private final TeamsPlugin teamsPlugin;

    public DamageListener(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity damaged = e.getEntity();
        if (damager instanceof Player && damaged instanceof Player) {
            Player pdamager = (Player) damager;
            Player pdamaged = (Player) damaged;
            String drteamName = teamsPlugin.getteamsConfig().getString(pdamager.getUniqueId().toString());
            String ddteamName = teamsPlugin.getteamsConfig().getString(pdamaged.getUniqueId().toString());
            if (drteamName.equalsIgnoreCase(ddteamName)) {
                e.setCancelled(true);
                pdamager.sendTitle(ChatColor.DARK_RED + "No! ", ChatColor.WHITE + "Do not hit teammates.", 15, 15, 15);
            } else {
                if (Team.valueOf(teamsPlugin.getteamsConfig().getString(pdamager.getUniqueId().toString())) == Team.TAMER) {
                    if (!pdamaged.hasPotionEffect(PotionEffectType.WITHER)) {
                        pdamaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 150, 1));
                    }

                } else if (Team.valueOf(teamsPlugin.getteamsConfig().getString(pdamager.getUniqueId().toString())) == Team.MAGE) {
                    if (!pdamager.hasPotionEffect(PotionEffectType.SPEED)) {
                        pdamager.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 2, false, false));
                    }
                } else if (Team.valueOf(teamsPlugin.getteamsConfig().getString(pdamager.getUniqueId().toString())) == Team.ARCHER) {
                    pdamaged.setHealth(pdamaged.getHealth() - 1.5);
                } else if (Team.valueOf(teamsPlugin.getteamsConfig().getString(pdamager.getUniqueId().toString())) == Team.KNIGHT) {
                    double dur = 0.5;
                    if (!pdamaged.hasPotionEffect(PotionEffectType.LEVITATION)) {
                        pdamaged.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 1));
                        pdamaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 1));
                    }
                }
            }
        }
        if (damager instanceof Player) {
            Player p = (Player) e.getDamager();
            if (Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString())) == Team.ARCHER) {
                e.setDamage(3);
            }
        }
        if (damager instanceof Zombie || damager instanceof Phantom || damager instanceof Spider
        || damager instanceof Piglin || damager instanceof PiglinBrute || damager instanceof Ravager || damager instanceof Pillager) {
            if (damaged instanceof Player) {
                Player p = (Player) e.getEntity();
                if (Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString())) == Team.KNIGHT) {
                    e.setDamage(e.getDamage() * 0.5);
                }
            }
        }
        if (damager instanceof Arrow){
            Arrow a = (Arrow) e.getDamager();
            if(a.getShooter() instanceof Skeleton || a.getShooter() instanceof Pillager) {
                if (damaged instanceof Player) {
                    Player p = (Player) e.getEntity();
                    if (Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString())) == Team.KNIGHT) {
                        e.setDamage(e.getDamage() * 0.5);
                    }
                }
            }
        }
           if (damager instanceof Player && (!(damaged instanceof Player))) {
               Player p = (Player) damager;
               if (Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString())) == Team.MAGE) {
                   ((LivingEntity) damaged).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false));
               }
           }
        if (damager instanceof Witch) {
            if (damaged instanceof Player) {
                Player p = (Player) e.getEntity();
                if (Team.valueOf(teamsPlugin.getteamsConfig().getString(p.getUniqueId().toString())) == Team.ARCHER) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
