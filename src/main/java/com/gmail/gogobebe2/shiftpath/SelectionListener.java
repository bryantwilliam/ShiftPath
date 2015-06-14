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

import java.util.*;

public class SelectionListener implements Listener {
    private Map<UUID, List<Location>> paths = new HashMap<>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (ShiftPath.isItemWand(player.getItemInHand())) {
            Action action = event.getAction();
            UUID uuid = player.getUniqueId();
            if (action == Action.LEFT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();

                List<Location> path = paths.get(uuid);
                if (path == null) {
                    path = new ArrayList<>();
                }
                path.add(block.getLocation());
                paths.put(uuid, path);

                player.sendMessage(ChatColor.DARK_GREEN + "Path number" + path.size() + " defined");
            }
            else if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
                int pAmount = 0;
                if (paths.get(uuid) != null) {
                    pAmount = paths.get(uuid).size();
                }
                // TODO: null
                player.sendMessage(ChatColor.DARK_GREEN + "Saved path " + pAmount + " as path id " + null + " in config.yml");
            }
        }
    }
}
