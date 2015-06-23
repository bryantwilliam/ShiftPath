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
        Bukkit.getLogger().severe("sel1X:" + selection1.getBlockX());
        Bukkit.getLogger().severe("sel1Y:" + selection1.getBlockY());
        Bukkit.getLogger().severe("sel1Z:" + selection1.getBlockZ());
        Bukkit.getLogger().severe("sel2X:" + selection2.getBlockX());
        Bukkit.getLogger().severe("sel2Y:" + selection2.getBlockY());
        Bukkit.getLogger().severe("sel2Z:" + selection2.getBlockZ());
        Location biggestSelection;
        Location smallestSelection;
        if (selection1.getBlockX() > selection2.getBlockX()) {
            biggestSelection = selection1;
            smallestSelection = selection2;
        }
        else {
            biggestSelection = selection2;
            smallestSelection = selection1;
        }

        for (int x = smallestSelection.getBlockX(); x <= biggestSelection.getBlockX(); x++) {
            for (int y = smallestSelection.getBlockY(); y <= biggestSelection.getBlockY(); y++) {
                for (int z = smallestSelection.getBlockZ(); z <= biggestSelection.getBlockZ(); z++) {
                    structure.add(selection1.getWorld().getBlockAt(x, y, z));
                    centerX += x; centerY += y; centerZ += z;
                    Bukkit.getLogger().severe("x1: " + centerX);
                    Bukkit.getLogger().severe("y1: " + centerY);
                    Bukkit.getLogger().severe("z1: " + centerZ);
                }
            }
        }
        if (centerX != 0) centerX /= structure.size();
        if (centerY != 0) centerY /= structure.size();
        if (centerY != 0) centerZ /= structure.size();
        Bukkit.getLogger().severe("x2: " + centerX);
        Bukkit.getLogger().severe("y2: " + centerY);
        Bukkit.getLogger().severe("z2: " + centerZ);
        this.center = selection1.getWorld().getBlockAt(centerX, centerY, centerZ).getLocation();
    }

    public void move(Location destination) {
        int xDistance = destination.getBlockX() - center.getBlockX();
        int yDistance = destination.getBlockY() - center.getBlockY();
        int zDistance = destination.getBlockZ() - center.getBlockZ();
        Bukkit.getLogger().severe("xDis: " + xDistance);
        Bukkit.getLogger().severe("yDis: " + yDistance);
        Bukkit.getLogger().severe("zDis: " + zDistance);
        Bukkit.getLogger().severe("---------------------");

        Set<Block> blocks = new HashSet<>();
        for (Block block : structure) {
            Block newBlock = block.getWorld().getBlockAt(block.getLocation().clone().add(xDistance, yDistance, zDistance));
            Bukkit.getLogger().severe("block.getLocation(): " + block.getLocation());
            newBlock.setType(block.getType());
            Bukkit.getLogger().severe("block.getType(): " + block.getType());
            newBlock.getState().update();
            block.setType(Material.AIR);
            block.getState().update();
            blocks.add(newBlock);
        }

        structure = blocks;
        center = center.clone().add(xDistance, yDistance, zDistance);
        Bukkit.getLogger().severe("center: " + center);
        selection1 = selection1.add(xDistance, yDistance, zDistance);
        Bukkit.getLogger().severe("selection1: " + selection1);
        selection2 = selection2.add(xDistance, yDistance, zDistance);
        Bukkit.getLogger().severe("selection2: " + selection2);
    }

    public Location getCenter() {
        return center;
    }
}
