package com.gmail.gogobebe2.shiftpath;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectionListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (ShiftPath.isItemWand(player.getItemInHand())) {
            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_BLOCK) {
                PathInProgress pathInProgress = new PathInProgress(player);

                player.sendMessage(ChatColor.DARK_GREEN + "Point number " + null
                        + " defined for new path which has not been saved yet."
                        + ChatColor.GREEN + " (Right click to save the path of selected points)");
            }
        }
    }

    private PathInProgress getPathFromOwner(Player owner) {
        for (PathInProgress pathInProgress : PathInProgress.getPathsInProgress()) {
            if (pathInProgress.getOwner().getUniqueId().equals(owner.getUniqueId())) {
                return pathInProgress;
            }
        }
        PathInProgress pathInProgress = new PathInProgress(owner);
        PathInProgress.getPathsInProgress().add(pathInProgress);
        return pathInProgress;
    }
}
