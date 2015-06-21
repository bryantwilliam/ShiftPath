package com.gmail.gogobebe2.shiftpath.path;

import com.gmail.gogobebe2.shiftpath.ShiftPath;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class Path {
    private List<Location> path = new ArrayList<>();

    private ShiftPath plugin;

    public Path(ShiftPath plugin) {
        this.plugin = plugin;
    }

    public List<Location> getPath() {
        return this.path;
    }

    public ShiftPath getPlugin() {
        return plugin;
    }
}
