package com.gmail.gogobebe2.shiftpath;

import org.bukkit.plugin.java.JavaPlugin;

    public class ShiftPath extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting up ShiftPath. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling ShiftPath. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }
}
