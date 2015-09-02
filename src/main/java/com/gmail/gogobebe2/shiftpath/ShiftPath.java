package com.gmail.gogobebe2.shiftpath;

import com.gmail.gogobebe2.shiftpath.path.ActivePath;
import com.gmail.gogobebe2.shiftpath.path.PathInConstruction;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShiftPath extends JavaPlugin {
    private static final ItemStack WAND = createWand();
    private static final String WORLD_LOCATION_CONFIG_PATH = "Immutable world file path";

    @Override
    public void onEnable() {
        getLogger().info("Starting up ShiftPath. If you need me to update this plugin, email at gogobebe2@gmail.com");
        for (World world : Bukkit.getWorlds()) {
            world.setAutoSave(false);
            // This isn't working on this guy's host. So I ended up implementing resetWorld.
            // I'm going to keep this here anyway.
        }

        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new SelectionListener(this), this);

        if (getConfig().isSet("Paths")) {
            for (String id : getConfig().getConfigurationSection("Paths").getKeys(false)) {
                ActivePath.getActivePaths().add(new ActivePath(Integer.parseInt(id), this));
            }
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    for (ActivePath activePath : ActivePath.getActivePaths()) {
                        activePath.approachNextPoint();
                    }
                }
            }, 0L, 20L);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling ShiftPath. If you need me to update this plugin, email at gogobebe2@gmail.com");
        for (World world : Bukkit.getWorlds()) {
            resetWorld(world, this);
        }
    }

    private static void resetWorld(World world, ShiftPath plugin) {
        try {
            File destination = world.getWorldFolder();

            Bukkit.unloadWorld(world, true);
            FileUtils.cleanDirectory(destination);

            File constantWorld;
            if (plugin.getConfig().isSet(WORLD_LOCATION_CONFIG_PATH)) {
                constantWorld = new File(plugin.getConfig().getString(WORLD_LOCATION_CONFIG_PATH));
            }
            else {
                throw new NullPointerException("No world in world path!");
            }

            FileUtils.copyDirectory(constantWorld, destination);

            plugin.getLogger().info("contents of " + constantWorld.getName() + " copied into to " + destination.getName());
        }
        catch (NullPointerException | IOException ex) {
            plugin.getLogger().severe(ex.getMessage());
        }
    }


    private static ItemStack createWand() {
        ItemStack wand = new ItemStack(Material.STICK, 1);
        ItemMeta wandMeta = wand.getItemMeta();
        List<String> wandLore = new ArrayList<>();

        wandMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.MAGIC + "[" + ChatColor.LIGHT_PURPLE
                + ChatColor.BOLD + "Selection Wand" + ChatColor.BLUE + "" + ChatColor.MAGIC + "]");
        wandLore.add(ChatColor.GREEN + "Right click" + ChatColor.AQUA + " to define a the region ");
        wandLore.add(ChatColor.AQUA + " of the platform that will move.");
        wandLore.add(ChatColor.GREEN + "Left click" + ChatColor.AQUA + " to define the path");
        wandLore.add(ChatColor.GREEN + "Type " + ChatColor.DARK_GREEN + "/sp set" + ChatColor.AQUA + " to save selections made");

        wandMeta.setLore(wandLore);
        wand.setItemMeta(wandMeta);
        return wand;
    }

    public static boolean isItemWand(ItemStack stick) throws NullPointerException {
        if (stick == null || !stick.hasItemMeta() || !stick.getItemMeta().hasLore()) {
            return false;
        }
        List<String> stickLore = stick.getItemMeta().getLore();
        return stickLore != null && stick.getType() == Material.STICK && stickLore.equals(WAND.getItemMeta().getLore());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("shiftpath") || label.equalsIgnoreCase("sp")) {
            if (!sender.hasPermission("shiftpath.*")) {
                sender.sendMessage(ChatColor.RED + "Sorry kiddo, This command is only for big boys.");
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Error! You have to be a player to use this command!");
                return true;
            }

            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage("Welcome to this shit looking help menu I made in less than 30 seconds.");
                player.sendMessage("To get a wand, type " + ChatColor.GREEN + "/sp wand");
                player.sendMessage("After you've selected your regions and paths with the wand, use "
                        + ChatColor.GREEN + "/sp set"
                        + ChatColor.RESET + " and restart the server for it to take effect.");
                player.sendMessage("If you make a change to the world you want to save, type " + ChatColor.GREEN
                        + "/saveworld" + ChatColor.RESET + " after.");
                return true;
            } else if (args[0].equalsIgnoreCase("saveworld") || args[0].equalsIgnoreCase("save")) {
                for (World world : Bukkit.getWorlds()) {
                    world.save();
                }
                player.sendMessage(ChatColor.GREEN + "Worlds saved!");
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                if (PathInConstruction.getPathInProgress(player, this).save()) {
                    player.sendMessage(ChatColor.GREEN + "Saved path in config.");
                } else {
                    player.sendMessage(ChatColor.RED + "Error! You have not defined the required points to set a new path.");
                }
                return true;
            } else if (args[0].equalsIgnoreCase("wand")) {
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
                } else {
                    int slot = inventory.firstEmpty();
                    if (slot == -1) {
                        player.sendMessage(ChatColor.RED + "Error! Not enough space in your inventory!");
                        return true;
                    } else {
                        inventory.setItem(slot, wand);
                    }

                }
                player.updateInventory();
                player.sendMessage(ChatColor.GREEN + "Selection Wand added to inventory");
                return true;
            }
        }
        return false;
    }
}
