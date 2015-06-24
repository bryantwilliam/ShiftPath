package com.gmail.gogobebe2.shiftpath.path;

import com.gmail.gogobebe2.shiftpath.LocationData;
import com.gmail.gogobebe2.shiftpath.Platform;
import com.gmail.gogobebe2.shiftpath.ShiftPath;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ActivePath extends Path {
    private static Set<ActivePath> activePaths = new HashSet<>();
    private Platform platform;
    private int currentGoalIndex = 0;

    public ActivePath(int pathID, ShiftPath plugin) {
        super(plugin);
        platform = new Platform(new LocationData("Paths." + pathID + ".selection1", getPlugin()).getLocation(),
                new LocationData("Paths." + pathID + ".selection2", getPlugin()).getLocation());
        for (String pointKey : getPlugin().getConfig().getConfigurationSection("Paths." + pathID + ".path").getKeys(false)) {
            getPath().add(new LocationData("Paths." + pathID + ".path." + pointKey, getPlugin()).getLocation());
        }
        activePaths.add(this);
    }

    public void approachNextPoint() {
        Bukkit.broadcastMessage("getPath().size(): " + getPath().size());
        Bukkit.broadcastMessage("currentGoalIndex: " + currentGoalIndex);
        if (getPath().size() != 1 && platform.getCenter().getBlock().getLocation().distance(getPath().get(currentGoalIndex).getBlock().getLocation()) == 0) {
            Bukkit.broadcastMessage("test1");
            // The goal as been reached.
            if (currentGoalIndex == getPath().size() - 1) {
                Bukkit.broadcastMessage("test2");
                Collections.reverse(getPath());
                currentGoalIndex = 1;
            } else {
                Bukkit.broadcastMessage("test3");
                currentGoalIndex++;
            }
        }
        Location currentGoal = getPath().get(currentGoalIndex).getBlock().getLocation();
        Location center = platform.getCenter().getBlock().getLocation();
        int xfactor = 0;
        int yfactor = 0;
        int zfactor = 0;

        if (center.getBlockX() < currentGoal.getBlockX()) {
            xfactor = 1;
        } else if (center.getBlockX() > currentGoal.getBlockX()) {
            xfactor = -1;
        }

        if (center.getBlockY() < currentGoal.getBlockY()) {
            yfactor = 1;
        } else if (center.getBlockY() > currentGoal.getBlockY()) {
            yfactor = -1;
        }

        if (center.getBlockZ() < currentGoal.getBlockZ()) {
            zfactor = 1;
        } else if (center.getBlockZ() > currentGoal.getBlockZ()) {
            zfactor = -1;
        }

        platform.move(center.clone().add(xfactor, yfactor, zfactor));
        Bukkit.broadcastMessage("test4");
    }

    public static Set<ActivePath> getActivePaths() {
        return activePaths;
    }
}