package com.gmail.gogobebe2.shiftpath;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ShiftPath extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting up ShiftPath. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling ShiftPath. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("shiftpath") || label.equalsIgnoreCase("sp")) {
            ItemStack wand = new ItemStack(Material.STICK, 1);
            ItemMeta wandMeta = wand.getItemMeta();
            List<String> wandLore = wandMeta.getLore();
            wandMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.MAGIC + "[" + ChatColor.LIGHT_PURPLE
                    + ChatColor.BOLD + "Wand" + ChatColor.BLUE + "" + ChatColor.MAGIC + "]");
            wandLore.clear();
            wandLore.add(ChatColor.GREEN + "Left click to define a platform path.");
            wandLore.add(ChatColor.AQUA + "Right click to set the path defined.");
            return true;
        }
        return false;
    }
}
