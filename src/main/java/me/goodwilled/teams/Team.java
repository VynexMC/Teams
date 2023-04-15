package me.goodwilled.teams;

import me.goodwilled.teams.utils.ColourUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public enum Team {
    CITIZEN(Material.AIR, ColourUtils.colour("&fCitizen"), "This person has no powers.") {
        @Override
        public void applyJoinPerks(Player player) {
            // Nothing to do. Citizens are suck.
        }

        @Override
        public double applyCombatPerks(Player attacker, Player victim, double baseDamage) {
            // ...
            return baseDamage;
        }

        @Override
        public double applyCombatPerks(Player attacker, Entity victim, double baseDamage) {
            // Still boring.
            return baseDamage;
        }

        @Override
        public double applyCombatProtections(Entity attacker, Player victim, double baseDamage) {
            return baseDamage;
        }
    },
    KNIGHT(Material.DIAMOND_SWORD, ColourUtils.colour("&3Knight"),
            "&aNot affected by soulsand.",
            "&aAll chestplates have Thorns when equipped.",
            "&aIssues 5s of poison when fighting players. &7(30s cooldown)") {
        private final List<EntityType> protectedEntities = Arrays.asList(
                EntityType.ZOMBIE, EntityType.PHANTOM, EntityType.SPIDER,
                EntityType.PIGLIN, EntityType.PIGLIN_BRUTE,
                EntityType.RAVAGER, EntityType.PILLAGER, EntityType.SKELETON
        );

        @Override
        public void applyJoinPerks(Player player) {
        }

        @Override
        public double applyCombatPerks(Player attacker, Player victim, double baseDamage) {
            if (!victim.hasPotionEffect(PotionEffectType.LEVITATION)) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 1));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 1));
            }
            return baseDamage;
        }

        @Override
        public double applyCombatPerks(Player attacker, Entity victim, double baseDamage) {
            return baseDamage;
        }

        @Override
        public double applyCombatProtections(Entity attacker, Player victim, double baseDamage) {
            if (this.protectedEntities.contains(attacker.getType())) {
                return baseDamage / 2.0d;
            }
            return baseDamage;
        }
    },
    MAGE(Material.ENDER_PEARL, ColourUtils.colour("&2Mage"),
            "&aIssues 5s of blindness when fighting players. &7(30s cooldown)",
            "&aImmune to magic by Witches.",
            "&aIssues fire aspect against entities and blocks when punching with fist. &7(30s cooldown)") {
        @Override
        public void applyJoinPerks(Player player) {
            player.setWalkSpeed(0.3f);
        }

        @Override
        public double applyCombatPerks(Player attacker, Player victim, double baseDamage) {
            if (!attacker.hasPotionEffect(PotionEffectType.SPEED)) {
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 2, false, false));
            }
            return baseDamage;
        }

        @Override
        public double applyCombatPerks(Player attacker, Entity victim, double baseDamage) {
            return baseDamage;
        }

        @Override
        public double applyCombatProtections(Entity attacker, Player victim, double baseDamage) {
            return baseDamage;
        }
    },
    ASSASSIN(Material.BAMBOO, ColourUtils.colour("&4Assassin"),
            "&a+0.3x walk speed increase.",
            "&aFreezes entities for 3 seconds when shooting with an arrow. &7(60s cooldown)",
            "&aGoes invisible for 3 sec when damaged by players. &7(60s cooldown)") {
        @Override
        public void applyJoinPerks(Player player) {
        }

        @Override
        public double applyCombatPerks(Player attacker, Player victim, double baseDamage) {
            if (!victim.hasPotionEffect(PotionEffectType.WITHER)) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 150, 1));
            }
            return baseDamage;
        }

        @Override
        public double applyCombatPerks(Player attacker, Entity victim, double baseDamage) {
            return baseDamage;
        }

        @Override
        public double applyCombatProtections(Entity attacker, Player victim, double baseDamage) {
            return baseDamage;
        }
    },
    VIKING(Material.DIAMOND_AXE, ColourUtils.colour("&6Viking"),
            "&aIssues 1.5x damage when using axes.",
            "&aDecreased hit delay when using axes.",
            "&c-0.3x walk speed.") {
        private final List<Material> axeMaterials = Arrays.asList(
                Material.WOODEN_AXE, Material.STONE_AXE,
                Material.GOLDEN_AXE, Material.IRON_AXE,
                Material.DIAMOND_AXE, Material.NETHERITE_AXE
        );

        @Override
        public void applyJoinPerks(Player player) {
        }

        @Override
        public double applyCombatPerks(Player attacker, Player victim, double baseDamage) {
            if (this.axeMaterials.contains(attacker.getInventory().getItemInMainHand().getType())) {
                return baseDamage * 1.5;
            }
            return baseDamage;
        }

        @Override
        public double applyCombatPerks(Player attacker, Entity victim, double baseDamage) {
            return baseDamage;
        }

        @Override
        public double applyCombatProtections(Entity attacker, Player victim, double baseDamage) {
            if (attacker instanceof Witch) {
                return -1.0d; // -1 cancels the damage event.
            }
            return baseDamage;
        }
    };

    private final String[] description;
    private final String prefix;
    private final Material icon;

    Team(Material icon, String prefix, String... description) {
        this.description = description;
        this.prefix = prefix;
        this.icon = icon;
    }

    // Perks applied to a player when they join the server.
    public abstract void applyJoinPerks(Player player);

    // Called in PvP situations.
    public abstract double applyCombatPerks(Player attacker, Player victim, double baseDamage);

    // Called in PvE situations.
    public abstract double applyCombatPerks(Player attacker, Entity victim, double baseDamage);

    public abstract double applyCombatProtections(Entity attacker, Player victim, double baseDamage);

    public String[] getDescription() {
        return this.description;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Material getIcon() {
        return this.icon;
    }
}
