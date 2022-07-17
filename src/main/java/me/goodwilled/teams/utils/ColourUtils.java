package me.goodwilled.teams.utils;

import net.md_5.bungee.api.ChatColor;

public final class ColourUtils {
    private ColourUtils() {
        throw new UnsupportedOperationException();
    }

    public static String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
