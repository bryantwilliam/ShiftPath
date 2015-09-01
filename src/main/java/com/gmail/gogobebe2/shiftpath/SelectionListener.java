package com.gmail.gogobebe2.shiftpath;

import com.gmail.gogobebe2.shiftpath.path.PathInConstruction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class SelectionListener implements Listener {
    private ShiftPath plugin;

    public SelectionListener(ShiftPath plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (ShiftPath.isItemWand(player.getItemInHand())) {
            Action action = event.getAction();
            Block block = event.getClickedBlock();
            PathInConstruction pathInConstruction = PathInConstruction.getPathInProgress(player, plugin);
            if (action == Action.RIGHT_CLICK_BLOCK) {
                pathInConstruction.createSelection(block.getLocation());
                player.sendMessage(ChatColor.DARK_GREEN + (pathInConstruction.isRegionDefined() ? "Second" : "First")
                        + " point for the cubic or trapezoid platform's region defined.");
                event.setCancelled(true);
            } else if (action == Action.LEFT_CLICK_BLOCK) {
                List<Location> path = pathInConstruction.getPath();
                path.add(block.getLocation());
                player.sendMessage(ChatColor.DARK_GREEN + "Path point number " + path.size() + " defined");
                event.setCancelled(true);
            }
        }
    }
}
