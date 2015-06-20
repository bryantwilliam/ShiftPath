package com.gmail.gogobebe2.shiftpath.path;

import com.gmail.gogobebe2.shiftpath.LocationData;
import com.gmail.gogobebe2.shiftpath.ShiftPath;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class PathInConstruction extends Path {
    private static Set<PathInConstruction> pathsInConstruction = new HashSet<>();
    private Player owner;

    private PathInConstruction(Player owner, ShiftPath plugin) {
        super(plugin);
        this.owner = owner;
        pathsInConstruction.add(this);
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean save() {
        if (getSelection1() != null && getSelection2() != null && getPath().size() > 0) {
            int id = 0;
            int latestPathID = 0;
            if (getPlugin().getConfig().isSet("Paths")) {
                Set<String> paths = getPlugin().getConfig().getConfigurationSection("Paths").getKeys(false);
                if (!paths.isEmpty()) {
                    id = Integer.parseInt(Collections.max(paths)) + 1;
                    String latestPoint = Collections.max(paths);
                    latestPathID = Integer.parseInt(latestPoint.substring(latestPoint.length() - 1));
                }
            }
            new LocationData(getSelection1(), getPlugin()).saveToConfig("Paths." + id + ".sel1");
            new LocationData(getSelection2(), getPlugin()).saveToConfig("Paths." + id + ".sel2");
            for (int p = 0; p < getPath().size(); p++) {
                Location point = getPath().get(p);
                new LocationData(point, getPlugin()).saveToConfig("Paths." + id + ".path.point" + latestPathID + p);
            }

            pathsInConstruction.remove(this);
            return true;
        }
        else {
            return false;
        }
    }



    public void createSelection(Location selection) {
        if (getSelection1() == null) {
            setSelection1(selection);
        } else if (getSelection2() == null) {
            setSelection2(selection);
        } else {
            setSelection2(null);
            setSelection1(selection);
        }
    }

    public static PathInConstruction getPathInProgress(Player owner, ShiftPath plugin) {
        for (PathInConstruction path : pathsInConstruction) {
            if (path.getOwner().getUniqueId().equals(owner.getUniqueId())) {
                return path;
            }
        }
        PathInConstruction path = new PathInConstruction(owner, plugin);
        pathsInConstruction.add(path);
        return path;
    }
}
