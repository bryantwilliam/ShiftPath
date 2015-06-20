package com.gmail.gogobebe2.shiftpath.path;

import com.gmail.gogobebe2.shiftpath.ShiftPath;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class Path {
    private List<Location> path = new ArrayList<>();
    private Location selection1 = null;
    private Location selection2 = null;
    private ShiftPath plugin;

    public Path(ShiftPath plugin) {
        this.plugin = plugin;
    }

    public List<Location> getPath() {
        return this.path;
    }

    public Location getSelection1() {
        return selection1;
    }

    public Location getSelection2() {
        return selection2;
    }

    public void setSelection1(Location selection1) {
        this.selection1 = selection1;
    }

    public void setSelection2(Location selection2) {
        this.selection2 = selection2;
    }

    public ShiftPath getPlugin() {
        return plugin;
    }
}
