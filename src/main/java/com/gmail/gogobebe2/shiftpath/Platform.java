package com.gmail.gogobebe2.shiftpath;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class Platform {
    private Location selection1;
    private Location selection2;
    private Location center;
    private Set<Block> structure = new HashSet<>();

    public Platform(Location selection1, Location selection2) {
        this.selection1 = selection1;
        this.selection2 = selection2;
        int centerX = 0, centerY = 0, centerZ = 0;
        int biggestX;
        int biggestY;
        int biggestZ;
        int smallestX;
        int smallestY;
        int smallestZ;

        if (selection1.getBlockX() > selection2.getBlockX()) {
            biggestX = selection1.getBlockX();
            smallestX = selection2.getBlockX();
        }
        else {
            biggestX = selection2.getBlockX();
            smallestX = selection1.getBlockX();
        }
        if (selection1.getBlockY() > selection2.getBlockY()) {
            biggestY = selection1.getBlockY();
            smallestY = selection2.getBlockY();
        }
        else {
            biggestY = selection2.getBlockY();
            smallestY = selection1.getBlockY();
        }
        if (selection1.getBlockZ() > selection2.getBlockZ()) {
            biggestZ = selection1.getBlockZ();
            smallestZ = selection2.getBlockZ();
        }
        else {
            biggestZ = selection2.getBlockZ();
            smallestZ = selection1.getBlockZ();
        }

        for (int x = smallestX; x <= biggestX; x++) {
            for (int y = smallestY; y <= biggestY; y++) {
                for (int z = smallestZ; z <= biggestZ; z++) {
                    structure.add(selection1.getWorld().getBlockAt(x, y, z));
                    centerX += x; centerY += y; centerZ += z;
                }
            }
        }
        if (centerX != 0) centerX /= structure.size();
        if (centerY != 0) centerY /= structure.size();
        if (centerY != 0) centerZ /= structure.size();
        this.center = selection1.getWorld().getBlockAt(centerX, centerY, centerZ).getLocation();
    }

    public void move(Location destination) {
        int xDistance = destination.getBlockX() - center.getBlockX();
        int yDistance = destination.getBlockY() - center.getBlockY();
        int zDistance = destination.getBlockZ() - center.getBlockZ();

        Set<Block> blocks = new HashSet<>();
        for (Block block : structure) {
            Block newBlock = block.getWorld().getBlockAt(block.getLocation().clone().add(xDistance, yDistance, zDistance));
            newBlock.setType(block.getType());
            newBlock.getState().update();
            blocks.add(newBlock);
        }
        for (Block oldBlock : structure) {
            boolean destroy = true;
            for (Block newBlock : blocks) {
                if (oldBlock.getLocation().equals(newBlock.getLocation())) {
                    destroy = false;
                }
            }
            if (destroy) {
                oldBlock.setType(Material.AIR);
            }
        }

        structure = blocks;
        center = center.clone().add(xDistance, yDistance, zDistance);
        selection1 = selection1.add(xDistance, yDistance, zDistance);
        selection2 = selection2.add(xDistance, yDistance, zDistance);
    }

    public Location getCenter() {
        return center;
    }
}
