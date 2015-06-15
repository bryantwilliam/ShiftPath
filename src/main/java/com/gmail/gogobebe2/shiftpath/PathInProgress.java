package com.gmail.gogobebe2.shiftpath;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathInProgress {
    private static Set<PathInProgress> pathsInProgress = new HashSet<>();

    private List<Location> path = new ArrayList<>();
    private final Location[] region = new Location[2];
    private Player owner;

    public PathInProgress(Player owner) {
        this.owner = owner;
        pathsInProgress.add(this);
    }

    public Player getOwner() {
        return this.owner;
    }

    public void save() {
        // TODO: save to config
        pathsInProgress.remove(this);
    }

    public List<Location> getPath() {
        return this.path;
    }

    public Location[] getRegion() {
        return this.region;
    }

    public static Set<PathInProgress> getPathsInProgress() {
        return pathsInProgress;
    }

}
