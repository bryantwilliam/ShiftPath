package com.gmail.gogobebe2.shiftpath;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class PathInProgress {
    private static Set<PathInProgress> pathsInProgress = new HashSet<>();

    private List<Location> path = new ArrayList<>();
    private Location selection1 = null;
    private Location selection2 = null;
    private Player owner;
    private ShiftPath plugin;

    private PathInProgress(Player owner, ShiftPath plugin) {
        this.owner = owner;
        this.plugin = plugin;
        pathsInProgress.add(this);
    }

    public Player getOwner() {
        return this.owner;
    }

    public void save() {
        int id = 0;
        String latestPathID = "path0";
        if (plugin.getConfig().isSet("Paths")) {
            Set<String> paths = plugin.getConfig().getConfigurationSection("Paths").getKeys(false);
            if (!paths.isEmpty()) {
                Set<Integer> ids = new HashSet<>(paths.size());
                for (String pID : paths) ids.add(Integer.parseInt(pID));
                id = Collections.max(ids) + 1;
                latestPathID = Collections.max(plugin.getConfig().getConfigurationSection("Paths." + id + "path").getKeys(false));
            }
        }
        new LocationData(selection1, plugin).saveToConfig("Paths." + id + ".sel1");
        new LocationData(selection2, plugin).saveToConfig("Paths." + id + ".sel2");
        for (int p = 0; p < path.size(); p++) {
            Location point = path.get(p);
            new LocationData(point, plugin).saveToConfig("Paths." + id + ".path." + Integer.parseInt(latestPathID.split("path")[1]) + p);
        }

        pathsInProgress.remove(this);
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

    public void createSelection(Location selection) {
        if (selection1 == null) {
            selection1 = selection;
        } else if (selection2 == null) {
            selection2 = selection;
        } else {
            selection2 = null;
            selection1 = selection;
        }
    }

    public static PathInProgress getPathInProgress(Player owner, ShiftPath plugin) {
        for (PathInProgress path : pathsInProgress) {
            if (path.getOwner().getUniqueId().equals(owner.getUniqueId())) {
                return path;
            }
        }
        PathInProgress path = new PathInProgress(owner, plugin);
        pathsInProgress.add(path);
        return path;
    }

}
