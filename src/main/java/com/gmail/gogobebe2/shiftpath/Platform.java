package com.gmail.gogobebe2.shiftpath;

import org.bukkit.Bukkit;
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
        for (int x = selection1.getBlockX(); x < selection2.getBlockX() + 1; x++) {
            for (int y = selection1.getBlockY(); y < selection2.getBlockY() + 1; y++) {
                for (int z = selection1.getBlockZ(); z < selection2.getBlockZ() + 1; z++) {
                    structure.add(selection1.getWorld().getBlockAt(x, y, z));
                    centerX += x; centerY += y; centerZ += z;
                    Bukkit.broadcastMessage("x: " + centerX);
                    Bukkit.broadcastMessage("y: " + centerY);
                    Bukkit.broadcastMessage("z: " + centerZ);
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
        Bukkit.broadcastMessage("xDis: " + xDistance);
        Bukkit.broadcastMessage("yDis: " + yDistance);
        Bukkit.broadcastMessage("zDis: " + zDistance);
        Bukkit.broadcastMessage("---------------------");

        Set<Block> blocks = new HashSet<>();
        for (Block block : structure) {
            Block newBlock = block.getWorld().getBlockAt(block.getLocation().clone().add(xDistance, yDistance, zDistance));
            Bukkit.broadcastMessage("block.getLocation(): " + block.getLocation());
            newBlock.setType(block.getType());
            Bukkit.broadcastMessage("block.getType(): " + block.getType());
            newBlock.getState().update();
            block.setType(Material.AIR);
            block.getState().update();
            blocks.add(newBlock);
        }

        structure = blocks;
        center = center.clone().add(xDistance, yDistance, zDistance);
        Bukkit.broadcastMessage("center: " + center);
        selection1 = selection1.add(xDistance, yDistance, zDistance);
        Bukkit.broadcastMessage("selection1: " + selection1);
        selection2 = selection2.add(xDistance, yDistance, zDistance);
        Bukkit.broadcastMessage("selection2: " + selection2);
    }

    public Location getCenter() {
        return center;
    }
}
