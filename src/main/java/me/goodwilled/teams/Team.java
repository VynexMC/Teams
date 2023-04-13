package me.goodwilled.teams;

import me.goodwilled.teams.utils.ColourUtils;
import org.bukkit.Material;

public enum Team {
    CITIZEN(Material.AIR, ColourUtils.colour("&fCitizen"), "This person has no powers."),
    KNIGHT(Material.DIAMOND_SWORD, ColourUtils.colour("&3Knight"), "First line", "Second line"),
    MAGE(Material.ENDER_PEARL, ColourUtils.colour("&2Mage"), "First line", "Second line"),
    ASSASSIN(Material.COW_SPAWN_EGG, ColourUtils.colour("&4Assassin"), "A friend of the animals, &4Assassins", "&fhave a strong army with them."),
    VIKING(Material.BOW, ColourUtils.colour("&6Viking"), "&6Vikings &fare skilled with bows and", "are known to be tough to kill.");

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
