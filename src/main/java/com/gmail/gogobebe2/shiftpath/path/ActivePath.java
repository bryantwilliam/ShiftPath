package com.gmail.gogobebe2.shiftpath.path;

import com.gmail.gogobebe2.shiftpath.LocationData;
import com.gmail.gogobebe2.shiftpath.Platform;
import com.gmail.gogobebe2.shiftpath.ShiftPath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class ActivePath extends Path {
    private static Set<ActivePath> activePaths = new HashSet<>();
    private Platform platform;
    private int currentGoalIndex = 0;

    //DEBUG pathID
    private int pathID;

    public ActivePath(int pathID, ShiftPath plugin) {
        super(plugin);
        LocationData selection1 = new LocationData("Paths." + pathID + ".selection1", getPlugin());
        LocationData selection2 = new LocationData("Paths." + pathID + ".selection2", getPlugin());
        platform = new Platform(selection1.getLocation(), selection2.getLocation());
        for (String pointKey : getPlugin().getConfig().getConfigurationSection("Paths." + pathID + ".path").getKeys(false)) {
            LocationData point = new LocationData("Paths." + pathID + ".path." + pointKey, getPlugin());
            getPath().add(point.getLocation());
        }
        activePaths.add(this);

        //DEBUG pathID
        this.pathID = pathID;
    }

    public void approachNextPoint() {
        Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "pathID: " + pathID);
        if (getPath().size() != 1) {
            Location currentGoal = getPath().get(currentGoalIndex).getBlock().getLocation();
            Location center = platform.getCenter().getBlock().getLocation();
            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "currentGoal: " + currentGoal);
            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "center: " + center);

            if (center.distance(currentGoal) != 0) {
                int xFactor = 0;
                int yFactor = 0;
                int zFactor = 0;

                if (center.getBlockX() < currentGoal.getBlockX()) {
                    xFactor = 1;
                } else if (center.getBlockX() > currentGoal.getBlockX()) {
                    xFactor = -1;
                }

                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "xFactor: " + xFactor);

                if (center.getBlockY() < currentGoal.getBlockY()) {
                    yFactor = 1;
                } else if (center.getBlockY() > currentGoal.getBlockY()) {
                    yFactor = -1;
                }

                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "yFactor: " + yFactor);

                if (center.getBlockZ() < currentGoal.getBlockZ()) {
                    zFactor = 1;
                } else if (center.getBlockZ() > currentGoal.getBlockZ()) {
                    zFactor = -1;
                }

                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "zFactor: " + zFactor);

                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "platform: " + platform);
                platform.move(center.clone().add(xFactor, yFactor, zFactor));
                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "(2)platform: " + platform);
            }
            else {
                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "DEBUG: " + ChatColor.RESET + "currentGoalIndex: " + currentGoalIndex);
                // The goal as been reached.
                if (currentGoalIndex == getPath().size() - 1) {
                    // If I want to to just reverse:
//                    Collections.reverse(getPath());
//                    currentGoalIndex = 1;
                    currentGoalIndex = 0;
                } else {
                    currentGoalIndex++;
                }
            }
        }
    }

    public static Set<ActivePath> getActivePaths() {
        return activePaths;
    }
}