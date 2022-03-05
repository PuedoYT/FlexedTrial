package me.puedo.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

    ArrayList<UUID> inStaffMod = new ArrayList<>();
    ArrayList<UUID> isSCToggled = new ArrayList<>();
    ArrayList<UUID> isVanished = new ArrayList<>();
    ArrayList<UUID> freezedPlayers = new ArrayList<>();

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        registerCommand();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommand(){
        getCommand("staffmod").setExecutor(new StaffModHandler());
        getCommand("vanish").setExecutor(new StaffModHandler());
        getCommand("freeze").setExecutor(new StaffModHandler());
        getCommand("tpto").setExecutor(new StaffModHandler());
        getCommand("staffchat").setExecutor(new StaffModHandler());
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new StaffModeEventsListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }
}
