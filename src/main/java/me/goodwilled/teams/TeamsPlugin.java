package me.goodwilled.teams;

import me.goodwilled.teams.commands.TeamsCommand;
import me.goodwilled.teams.listeners.*;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;

public class TeamsPlugin extends JavaPlugin {
    public static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Teams" + ChatColor.DARK_GRAY + "] ";

    public static Inventory createteamsGui() {
        Inventory inventory = Bukkit.createInventory(null, 27, "Teams");
        ItemStack knight = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemStack mage = new ItemStack(Material.ENDER_PEARL, 1);
        ItemStack tamer = new ItemStack(Material.COW_SPAWN_EGG, 1);
        ItemStack archer = new ItemStack(Material.BOW, 1);
        ItemMeta aMeta = knight.getItemMeta();
        ItemMeta nMeta = mage.getItemMeta();
        ItemMeta wMeta = tamer.getItemMeta();
        ItemMeta vMeta = archer.getItemMeta();
        aMeta.setDisplayName(ChatColor.BLUE + "KNIGHT");
        nMeta.setDisplayName(ChatColor.DARK_GREEN + "MAGE");
        wMeta.setDisplayName(ChatColor.DARK_RED + "TAMER");
        vMeta.setDisplayName(ChatColor.GOLD + "ARCHER");
        aMeta.setLore(Collections.singletonList("Ability to craft swords."));
        nMeta.setLore(Collections.singletonList("Magic."));
        wMeta.setLore(Collections.singletonList("Shoot like a Skeleton."));
        vMeta.setLore(Collections.singletonList("Animal army!"));
        knight.setItemMeta(aMeta);
        archer.setItemMeta(vMeta);
        mage.setItemMeta(nMeta);
        tamer.setItemMeta(wMeta);
        inventory.setItem(10, knight);
        inventory.setItem(12, mage);
        inventory.setItem(14, tamer);
        inventory.setItem(16, archer);
        return inventory;
    }

    private final Path teamsConfigPath = this.getDataFolder().toPath().resolve("teams.yml");

    private FileConfiguration teamsConfig;
    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.loadteamsConfig();
        this.getCommand("teams").setExecutor(new TeamsCommand());
        this.initListeners();
    }

    private void loadteamsConfig() {
        this.saveResource("teams.yml", false);
        try (final BufferedReader reader = Files.newBufferedReader(this.teamsConfigPath)) {
            this.teamsConfig = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListeners() {
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListener(this), this);
        pluginManager.registerEvents(new ConnectionListener(this), this);
        pluginManager.registerEvents(new DamageListener(this), this);
        pluginManager.registerEvents(new InventoryListener(this), this);
        pluginManager.registerEvents(new MiscListener(this), this);
    }

    public void saveteamsConfig() {
        try {
            this.teamsConfig.save(this.teamsConfigPath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<LuckPerms> getLuckPerms() {
        if (this.luckPerms == null) {
            final RegisteredServiceProvider<LuckPerms> provider = this.getServer().getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                this.luckPerms = provider.getProvider();
            }
        }
        return Optional.ofNullable(this.luckPerms);
    }

    public FileConfiguration getteamsConfig() {
        return this.teamsConfig;
    }
}

