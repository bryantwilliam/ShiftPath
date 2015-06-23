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
    private Set<Block> blocks = new HashSet<>();

    public Platform(Location selection1, Location selection2) {
        this.selection1 = selection1;
        this.selection2 = selection2;
        int centerX = 0, centerY = 0, centerZ = 0;
        for (int x = selection1.getBlockX(); x < selection2.getBlockX(); x++) {
            for (int y = selection1.getBlockY(); y < selection2.getBlockY(); y++) {
                for (int z = selection1.getBlockZ(); z < selection2.getBlockZ(); z++) {
                    blocks.add(selection1.getWorld().getBlockAt(x, y, z));
                    centerX += x; centerY += y; centerZ += z;
                }
            }
        }
        centerX /= blocks.size(); centerY /= blocks.size(); centerZ /= blocks.size();
        this.center = selection1.getWorld().getBlockAt(centerX, centerY, centerZ).getLocation();
    }

    public void move(Location destination) {
        // TODO: use in ActivePath.
        int xDistance = destination.getBlockX() - center.getBlockX();
        int yDistance = destination.getBlockY() - center.getBlockY();
        int zDistance = destination.getBlockZ() - center.getBlockZ();

        Set<Block> newBlocks = new HashSet<>();
        for (Block block : blocks) {
            Block newBlock = block.getWorld().getBlockAt(block.getLocation().clone().add(xDistance, yDistance, zDistance));
            newBlock.setType(block.getType());
            newBlock.getState().setData(block.getState().getData());
            newBlock.getState().update();
            block.setType(Material.AIR);
            newBlocks.add(block);
        }

        blocks = newBlocks;
        center = center.clone().add(xDistance, yDistance, zDistance);
        selection1 = selection1.clone().add(xDistance, yDistance, zDistance);
        selection2 = selection2.clone().add(xDistance, yDistance, zDistance);
    }

    public Location getCenter() {
        return center;
    }
}
