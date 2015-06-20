package com.gmail.gogobebe2.shiftpath.path;

import com.gmail.gogobebe2.shiftpath.LocationData;
import com.gmail.gogobebe2.shiftpath.ShiftPath;

import java.util.HashSet;
import java.util.Set;

public class ActivePath extends Path {
    private static Set<ActivePath> activePaths = new HashSet<>();
    private Platform platform;

    public ActivePath(ShiftPath plugin, int pathID) {
        super(plugin);
        setSelection1(new LocationData("Paths." + pathID + ".sel1", getPlugin()).getLocation());
        setSelection2(new LocationData("Paths." + pathID + ".sel2", getPlugin()).getLocation());
        this.platform = new Platform(getSelection1(), getSelection2());
        for (String pointKey : getPlugin().getConfig().getConfigurationSection("Paths." + pathID + ".path").getKeys(false)) {
            getPath().clear();
            getPath().add(new LocationData("Paths." + pathID + ".path." + pointKey, getPlugin()).getLocation());
        }
    }
}