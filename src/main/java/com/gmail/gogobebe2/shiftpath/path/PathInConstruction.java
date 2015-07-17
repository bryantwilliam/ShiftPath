package com.gmail.gogobebe2.shiftpath.path;

import com.gmail.gogobebe2.shiftpath.LocationData;
import com.gmail.gogobebe2.shiftpath.ShiftPath;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PathInConstruction extends Path {
    private static Set<PathInConstruction> pathsInConstruction = new HashSet<>();
    private Player owner;
    private Location selection1;
    private Location selection2;

    private PathInConstruction(Player owner, ShiftPath plugin) {
        super(plugin);
        this.owner = owner;
        pathsInConstruction.add(this);
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean save() {
        if (selection1 != null && selection2 != null && getPath().size() > 0) {
            int id = 0;
            if (getPlugin().getConfig().isSet("Paths")) {
                Set<String> paths = getPlugin().getConfig().getConfigurationSection("Paths").getKeys(false);
                if (!paths.isEmpty()) {
                    for (String p : paths) {
                        int pathID = Integer.parseInt(p);
                        if (pathID > id) {
                            id = pathID + 1;
                        }
                    }
                }
            }
            new LocationData(selection1, getPlugin()).saveToConfig("Paths." + id + ".selection1");
            new LocationData(selection2, getPlugin()).saveToConfig("Paths." + id + ".selection2");
            for (int p = 0; p < getPath().size(); p++) {
                Location point = getPath().get(p);
                new LocationData(point, getPlugin()).saveToConfig("Paths." + id + ".path.point" + p);
            }

            pathsInConstruction.remove(this);
            return true;
        } else {
            return false;
        }
    }


    public void createSelection(Location selection) {
        if (selection1 == null) {
            selection1 = selection;
        } else if (selection2 == null) {
            selection2 = selection;
        } else {
            selection1 = selection;
            selection2 = null;
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

    public boolean isRegionDefined() {
        return selection2 != null;
    }
}
