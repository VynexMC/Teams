package me.goodwilled.teams;

import me.goodwilled.teams.commands.TeamsCommand;
import me.goodwilled.teams.listeners.*;
import me.goodwilled.teams.manager.TeamManager;
import me.goodwilled.teams.storage.MySqlStorage;
import me.goodwilled.teams.storage.Storage;
import me.goodwilled.teams.storage.YamlStorage;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.ChatColor;
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

    public static final String TEAMS_GUI_TITLE = "Teams";

    public static Inventory createTeamsGui() {
        Inventory inventory = Bukkit.createInventory(null, 27, TEAMS_GUI_TITLE);
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

    private LuckPerms luckPerms;

    private TeamManager teamManager;

    private Storage storage;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.initStorage();
        this.getCommand("teams").setExecutor(new TeamsCommand());
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
        if (storageType.equalsIgnoreCase("YAML")) {
            this.storage = new YamlStorage(this.getDataFolder().toPath().resolve("teams.yml"));
        } else if (storageType.equalsIgnoreCase("MySQL")) {
            this.storage = new MySqlStorage(this.getConfig().getString("storage.credentials.host"),
                    this.getConfig().getInt("storage.credentials.port"),
                    this.getConfig().getString("storage.credentials.database"),
                    this.getConfig().getString("storage.credentials.username"),
                    this.getConfig().getString("storage.credentials.password")
            );
        } else {
            throw new IllegalStateException("Invalid storage type! (" + storageType + ")");
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

    public TeamManager getTeamManager() {
        return this.teamManager;
    }
}

