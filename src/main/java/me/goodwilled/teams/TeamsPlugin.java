package me.goodwilled.teams;

import me.goodwilled.teams.commands.TeamsCommand;
import me.goodwilled.teams.listeners.ChatListener;
import me.goodwilled.teams.listeners.ConnectionListener;
import me.goodwilled.teams.listeners.DamageListener;
import me.goodwilled.teams.listeners.InventoryListener;
import me.goodwilled.teams.listeners.MiscListener;
import me.goodwilled.teams.manager.TeamManager;
import me.goodwilled.teams.storage.MySqlStorage;
import me.goodwilled.teams.storage.Storage;
import me.goodwilled.teams.storage.YamlStorage;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.Optional;

public class TeamsPlugin extends JavaPlugin {
    public static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Teams" + ChatColor.DARK_GRAY + "] ";

    private LuckPerms luckPerms;
    private Economy economy;

    private TeamManager teamManager;
    private Storage storage;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.initStorage();
        this.getCommand("teams").setExecutor(new TeamsCommand(this));
        this.initListeners();
    }

    @Override
    public void onDisable() {
        this.storage.shutdown();
    }

    public long reload(String... flags) {
        final long start = System.currentTimeMillis();
        for (String flag : flags) {
            if (flag.equalsIgnoreCase("-full")) {
                this.teamManager.shutdown();
                this.storage.shutdown();
                this.initStorage();
                this.teamManager.loadAll();
                this.reloadConfig();
                break;
            }
            if (flag.equalsIgnoreCase("-config")) {
                this.reloadConfig();
            }
        }
        return System.currentTimeMillis() - start;
    }

    private void initListeners() {
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListener(this), this);
        pluginManager.registerEvents(new ConnectionListener(this), this);
        pluginManager.registerEvents(new DamageListener(this), this);
        pluginManager.registerEvents(new InventoryListener(this), this);
        pluginManager.registerEvents(new MiscListener(this), this);
    }

    private void initStorage() {
        final String storageType = this.getConfig().getString("storage.type");
        if (storageType == null) {
            throw new NullPointerException("Storage type cannot be null!");
        }
        switch (storageType) {
            case "YAML":
                this.storage = new YamlStorage(this.getDataFolder().toPath().resolve("teams.yml"));
            case "MySQL":
                this.storage = new MySqlStorage(this.getConfig().getString("storage.credentials.host"),
                        this.getConfig().getInt("storage.credentials.port"),
                        this.getConfig().getString("storage.credentials.database"),
                        this.getConfig().getString("storage.credentials.username"),
                        this.getConfig().getString("storage.credentials.password")
                );
            default:
                this.getLogger().warning("Invalid storage type: " + storageType + ". Defaulting to YAML.");
                this.storage = new YamlStorage(this.getDataFolder().toPath().resolve("teams.yml"));
        }
        this.storage.init();
        this.teamManager = new TeamManager(this.storage);
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

    public Optional<Economy> getEconomy() {
        if (this.economy == null) {
            final RegisteredServiceProvider<Economy> provider = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (provider != null) {
                this.economy = provider.getProvider();
            }
        }
        return Optional.ofNullable(this.economy);
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }
}

