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
        Entity entityAttacker = e.getDamager();
        Entity entityVictim = e.getEntity();
        if (entityAttacker instanceof Player && entityVictim instanceof Player) {
            Player attacker = (Player) entityAttacker;
            Player victim = (Player) entityVictim;

            final Team attackerTeam = this.teamsPlugin.getTeamManager().getTeam(attacker.getUniqueId());
            final Team victimTeam = this.teamsPlugin.getTeamManager().getTeam(victim.getUniqueId());
            if (attackerTeam == victimTeam) {
                e.setCancelled(true);
                attacker.sendTitle(ChatColor.DARK_RED + "No! ", ChatColor.WHITE + "Do not hit teammates.", 15, 15, 15);
            } else {
                if (attackerTeam == Team.ASSASSIN) {
                    if (!victim.hasPotionEffect(PotionEffectType.WITHER)) {
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 150, 1));
                    }
                } else if (attackerTeam == Team.MAGE) {
                    if (!attacker.hasPotionEffect(PotionEffectType.SPEED)) {
                        attacker.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 2, false, false));
                    }
                } else if (attackerTeam == Team.VIKING) {
                    victim.setHealth(victim.getHealth() - 1.5);
                } else if (attackerTeam == Team.KNIGHT) {
                    double dur = 0.5;
                    if (!victim.hasPotionEffect(PotionEffectType.LEVITATION)) {
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 1));
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 1));
                    }
                }
            }
        }
        if (entityAttacker instanceof Player) {
            Player attacker = (Player) e.getDamager();
            if (this.teamsPlugin.getTeamManager().getTeam(attacker.getUniqueId()) == Team.VIKING) {
                e.setDamage(3);
            }
        }
        if (entityAttacker instanceof Zombie || entityAttacker instanceof Phantom || entityAttacker instanceof Spider
                || entityAttacker instanceof Piglin || entityAttacker instanceof PiglinBrute || entityAttacker instanceof Ravager || entityAttacker instanceof Pillager) {
            if (entityVictim instanceof Player) {
                Player victim = (Player) e.getEntity();
                if (this.teamsPlugin.getTeamManager().getTeam(victim.getUniqueId()) == Team.KNIGHT) {
                    e.setDamage(e.getDamage() * 0.5);
                }
            }
        }
        if (entityAttacker instanceof Arrow) {
            Arrow a = (Arrow) e.getDamager();
            if (a.getShooter() instanceof Skeleton || a.getShooter() instanceof Pillager) {
                if (entityVictim instanceof Player) {
                    Player victim = (Player) e.getEntity();
                    if (this.teamsPlugin.getTeamManager().getTeam(victim.getUniqueId()) == Team.KNIGHT) {
                        e.setDamage(e.getDamage() * 0.5);
                    }
                }
            }
        }
        if (entityAttacker instanceof Player && (!(entityVictim instanceof Player))) {
            Player attacker = (Player) entityAttacker;
            // do some shit in here
            // What kind of shit we talking?
        }
        if (entityAttacker instanceof Witch) {
            if (entityVictim instanceof Player) {
                Player victim = (Player) e.getEntity();
                if (this.teamsPlugin.getTeamManager().getTeam(victim.getUniqueId()) == Team.VIKING) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
