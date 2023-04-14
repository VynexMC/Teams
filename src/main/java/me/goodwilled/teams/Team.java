package me.goodwilled.teams;

import me.goodwilled.teams.utils.ColourUtils;
import org.bukkit.Material;

public enum Team {
    CITIZEN(Material.AIR, ColourUtils.colour("&fCitizen"), "This person has no powers."),
    KNIGHT(Material.DIAMOND_SWORD, ColourUtils.colour("&3Knight"), "&aNot affected by soulsand.", "&aAll chestplates have Thorns when equipped.", "&aIssues 5s of poison when fighting players. &7(30s cooldown)"),
    MAGE(Material.ENDER_PEARL, ColourUtils.colour("&2Mage"), "&aIssues 5s of blindness when fighting players. &7(30s cooldown)", "&aImmune to magic by Witches.", "&aIssues fire aspect against entities and blocks when punching with fist. &7(30s cooldown)"),
    ASSASSIN(Material.BAMBOO, ColourUtils.colour("&4Assassin"), "&a+0.3x walk speed increase.", "&aFreezes entities for 3 seconds when shooting with an arrow. &7(60s cooldown)", "&aGoes invisible for 3 sec when damaged by players. &7(60s cooldown)"),
    VIKING(Material.DIAMOND_AXE, ColourUtils.colour("&6Viking"), "&aIssues 1.5x damage when using axes.", "&aDecreased hit delay when using axes.", "&c-0.3x walk speed.");

    private final String[] description;
    private final String prefix;
    private final Material icon;

    Team(Material icon, String prefix, String... description) {
        this.description = description;
        this.prefix = prefix;
        this.icon = icon;
    }

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
