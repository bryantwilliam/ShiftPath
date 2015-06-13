package com.gmail.gogobebe2.shiftpath;

import org.bukkit.Bukkit;
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
import java.util.HashMap;
import java.util.List;

public class ShiftPath extends JavaPlugin {
    private static final ItemStack WAND = createWand();

    @Override
    public void onEnable() {
        getLogger().info("Starting up ShiftPath. If you need me to update this plugin, email at gogobebe2@gmail.com");
        createWand();
        Bukkit.getPluginManager().registerEvents(new SelectionListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling ShiftPath. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }


    private static ItemStack createWand() {
        ItemStack wand = new ItemStack(Material.STICK, 1);
        ItemMeta wandMeta = wand.getItemMeta();
        List<String> wandLore = new ArrayList<>();

        wandMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.MAGIC + "[" + ChatColor.LIGHT_PURPLE
                + ChatColor.BOLD + "Selection Wand" + ChatColor.BLUE + "" + ChatColor.MAGIC + "]");
        wandLore.add(ChatColor.GREEN + "Left click to define a platform path.");
        wandLore.add(ChatColor.AQUA + "Right click to set the path defined.");

        wandMeta.setLore(wandLore);
        wand.setItemMeta(wandMeta);
        return wand;
    }

    public static boolean isItemWand(ItemStack stick) throws NullPointerException {
        try {
            List<String> stickLore = stick.getItemMeta().getLore();
            return stickLore != null && stick.getType() == Material.STICK && stickLore.equals(WAND.getItemMeta().getLore());
        }
        catch (NullPointerException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("shiftpath") || label.equalsIgnoreCase("sp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Error! You have to be a player to use this command!");
                return true;
            }
            Player player = (Player) sender;
            ItemStack wand = WAND.clone();

            PlayerInventory inventory = player.getInventory();
            ItemStack itemInHand = inventory.getItemInHand();

            HashMap<Integer, ? extends ItemStack> sticks = inventory.all(Material.STICK);
            if (!sticks.isEmpty()) {
                for (int stickIndex : sticks.keySet()) {
                    ItemStack stick = sticks.get(stickIndex);
                    if (isItemWand(stick)) {
                        inventory.setItemInHand(stick);
                        if (itemInHand != null) {
                            inventory.setItem(stickIndex, itemInHand);
                        }
                        player.sendMessage(ChatColor.GREEN + "You already have a Selection Wand in your inventory. It has been swapped with the item in your hand.");
                        return true;
                    }
                }

            }

            if (itemInHand == null || itemInHand.getType() == Material.AIR) {
                player.setItemInHand(wand);
            }
            else {
                int slot = inventory.firstEmpty();
                if (slot == -1) {
                    player.getLocation().getWorld().dropItemNaturally(player.getLocation(), wand);
                    player.sendMessage(ChatColor.RED + "No space in your inventory, dropped wand on floor.");
                    return true;
                }
                else {
                    inventory.setItem(slot, wand);
                }

            }
            player.sendMessage(ChatColor.GREEN + "Selection Wand added to inventory");
            return true;
        }
        return false;
    }
}
