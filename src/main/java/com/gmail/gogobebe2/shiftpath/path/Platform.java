package com.gmail.gogobebe2.shiftpath.path;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.Set;

public class Platform {
    private Location position1;
    private Location position2;
    private Set<Location> blocks;

    public Platform(Location position1, Location position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

    public void move(BlockFace direction) {

    }
}
