package com.gmail.gogobebe2.shiftpath.path;

import com.gmail.gogobebe2.shiftpath.LocationData;
import com.gmail.gogobebe2.shiftpath.Platform;
import com.gmail.gogobebe2.shiftpath.ShiftPath;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class ActivePath extends Path {
    private static Set<ActivePath> activePaths = new HashSet<>();
    private Location currentPoint;
    private Platform platform;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public ActivePath(ShiftPath plugin, int pathID) {
        super(plugin);
        setPlatform(new Platform(new LocationData("Paths." + pathID + ".sel1", getPlugin()).getLocation(),
                new LocationData("Paths." + pathID + ".sel2", getPlugin()).getLocation()));
        for (String pointKey : getPlugin().getConfig().getConfigurationSection("Paths." + pathID + ".path").getKeys(false)) {
            getPath().clear();
            getPath().add(new LocationData("Paths." + pathID + ".path." + pointKey, getPlugin()).getLocation());
        }
        currentPoint = getPath().get(0);
    }

    public void approachNextPoint() {
        // TODO: platform.move();
        // TODO: if platform center is now at the next point, create a new point.
        // TODO: if the current point is the last point, go to the first point.
    }
}