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
        } else {
            biggestX = selection2.getBlockX();
            smallestX = selection1.getBlockX();
        }
        if (selection1.getBlockY() > selection2.getBlockY()) {
            biggestY = selection1.getBlockY();
            smallestY = selection2.getBlockY();
        } else {
            biggestY = selection2.getBlockY();
            smallestY = selection1.getBlockY();
        }
        if (selection1.getBlockZ() > selection2.getBlockZ()) {
            biggestZ = selection1.getBlockZ();
            smallestZ = selection2.getBlockZ();
        } else {
            biggestZ = selection2.getBlockZ();
            smallestZ = selection1.getBlockZ();
        }
        System.out.println("smallestX: " + smallestX);
        System.out.println("smallestY: " + smallestY);
        System.out.println("smallestZ: " + smallestZ);
        System.out.println("biggestX: " + biggestX);
        System.out.println("biggestY: " + biggestY);
        System.out.println("biggestZ: " + biggestZ);
        int blockFrequency = 0;
        for (int x = smallestX; x <= biggestX; x++) {
            System.out.println("x: " + x);
            for (int y = smallestY; y <= biggestY; y++) {
                System.out.println("y: " + y);
                for (int z = smallestZ; z <= biggestZ; z++) {
                    System.out.println("z: " + z);
                    Block block = selection1.getWorld().getBlockAt(x, y, z);
                    if (block.getType() != Material.AIR) {
                        System.out.println("added");
                        structure.add(block);
                    }
                    centerX += x;
                    centerY += y;
                    centerZ += z;
                    blockFrequency++;
                    System.out.println("centerX: " + centerX);
                    System.out.println("centerY: " + centerY);
                    System.out.println("centerZ: " + centerZ);
                }
            }
        }
        if (centerX != 0) centerX /= blockFrequency;
        if (centerY != 0) centerY /= blockFrequency;
        if (centerZ != 0) centerZ /= blockFrequency;
        System.out.println("final centerX: " + centerX);
        System.out.println("final centerY: " + centerY);
        System.out.println("final centerZ: " + centerZ);
        this.center = selection1.getWorld().getBlockAt(centerX, centerY, centerZ).getLocation();
    }

    public void move(Location destination) {
        int xDistance = destination.getBlockX() - center.getBlockX();
        int yDistance = destination.getBlockY() - center.getBlockY();
        int zDistance = destination.getBlockZ() - center.getBlockZ();

        Set<Block> newStructure = new HashSet<>();
        for (Block oldBlock : structure) {
            Block newBlock = oldBlock.getWorld().getBlockAt(oldBlock.getLocation().clone().add(xDistance, yDistance, zDistance));
            newBlock.setType(oldBlock.getType());
            newStructure.add(newBlock);
        }
        for (Block oldBlock : structure) {
            boolean delete = true;
            for (Block newBlock : newStructure) {
                if (oldBlock.getLocation().clone().equals(newBlock.getLocation().clone())) {
                    delete = false;
                }
            }
            if (delete) {
                oldBlock.setType(Material.AIR);
            }
        }

        structure = newStructure;
        center = center.clone().add(xDistance, yDistance, zDistance);
        selection1 = selection1.add(xDistance, yDistance, zDistance);
        selection2 = selection2.add(xDistance, yDistance, zDistance);
    }

    public Location getCenter() {
        return center;
    }
}
