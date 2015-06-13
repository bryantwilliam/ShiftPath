package com.gmail.gogobebe2.shiftpath;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
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
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "Error! You have to be a player to use this command!");
                return true;
            }
            Player player = (Player) sender;

            ItemStack wand = new ItemStack(Material.STICK, 1);
            ItemMeta wandMeta = wand.getItemMeta();
            List<String> wandLore = new ArrayList<>();

            wandMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.MAGIC + "[" + ChatColor.LIGHT_PURPLE
                    + ChatColor.BOLD + "Selection Wand" + ChatColor.BLUE + "" + ChatColor.MAGIC + "]");
            wandLore.add(ChatColor.GREEN + "Left click to define a platform path.");
            wandLore.add(ChatColor.AQUA + "Right click to set the path defined.");

            wandMeta.setLore(wandLore);
            wand.setItemMeta(wandMeta);

            PlayerInventory inventory = player.getInventory();
            ItemStack itemInHand = inventory.getItemInHand();
            if (itemInHand == null || itemInHand.getType() == Material.AIR) {
                player.setItemInHand(wand);
            }
            else {
                inventory.setItem(inventory.firstEmpty(), wand);
            }
            player.sendMessage(ChatColor.GREEN + "Selection Wand added to inventory");
            return true;
        }
        return false;
    }
}
