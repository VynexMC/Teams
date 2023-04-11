package me.goodwilled.teams;

import me.goodwilled.teams.utils.ColourUtils;

public enum Team {
    CITIZEN(ColourUtils.colour("&fCitizen"), "This person has no powers."),
    KNIGHT(ColourUtils.colour("&9Knight"), "First line", "Second line"),
    MAGE(ColourUtils.colour("&2Mage"), "First line", "Second line"),
    ASSASSIN(ColourUtils.colour("&4Assassin"), "A friend of the animals, &4Assassins", "&fhave a strong army with them."),
    VIKING(ColourUtils.colour("&6Viking"), "&6Vikings &fare skilled with bows and", "are known to be tough to kill.");

    private final String[] description;
    private final String prefix;

    Team(String prefix, String... description) {
        this.description = description;
        this.prefix = prefix;
    }

    public String[] getDescription() {
        return this.description;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
