package com.gmail.gogobebe2.shiftpath;

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
            PathInProgress pathInProgress = PathInProgress.getPathInProgress(player, plugin);
            if (action == Action.RIGHT_CLICK_BLOCK) {
                pathInProgress.createSelection(block.getLocation());
                player.sendMessage(ChatColor.DARK_GREEN + (pathInProgress.getSelection2() == null ? "First" : "Second")
                        + " point for the cubic or trapezoid platform's region defined.");
                event.setCancelled(true);
            }
            else if (action == Action.LEFT_CLICK_BLOCK) {
                List<Location> path = pathInProgress.getPath();
                path.add(block.getLocation());
                player.sendMessage(ChatColor.DARK_GREEN + "Path point number " + path.size() + " defined");
                event.setCancelled(true);
            }
        }
    }

}
