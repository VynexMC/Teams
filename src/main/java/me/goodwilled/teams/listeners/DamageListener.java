package me.goodwilled.teams.listeners;

import me.goodwilled.teams.Team;
import me.goodwilled.teams.TeamsPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {
    private final TeamsPlugin teamsPlugin;

    public DamageListener(TeamsPlugin teamsPlugin) {
        this.teamsPlugin = teamsPlugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        final Entity attacker = e.getDamager();
        final Entity victim = e.getEntity();

        final Team attackerTeam = this.teamsPlugin.getTeamManager().getTeam(attacker.getUniqueId());

        if (attackerTeam == Team.CITIZEN) {
            return;
        }

        if (!(attacker instanceof Player)) {
            if (victim instanceof Player) {
                final double damage = attackerTeam.applyCombatProtections(attacker, (Player) victim, e.getDamage());
                if (damage == -1) {
                    e.setCancelled(true);
                }
                e.setDamage(damage);
            }
            return;
        }

        double finalDamage;
        if (victim instanceof Player) {
            final Team victimTeam = this.teamsPlugin.getTeamManager().getTeam(victim.getUniqueId());

            if (victimTeam == attackerTeam) {
                ((Player) attacker).sendTitle(ChatColor.DARK_RED + "No! ", ChatColor.WHITE + "Do not hit teammates.", 15, 15, 15);
                e.setCancelled(true);
                return;
            }

            finalDamage = attackerTeam.applyCombatPerks((Player) attacker, (Player) victim, e.getDamage());
        } else {
            finalDamage = attackerTeam.applyCombatPerks((Player) attacker, victim, e.getDamage());
        }

        if (finalDamage == -1) {
            e.setCancelled(true);
        } else {
            e.setDamage(finalDamage);
        }
    }

}
