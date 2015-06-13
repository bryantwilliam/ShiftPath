package com.gmail.gogobebe2.shiftpath;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SelectionListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (action == Action.LEFT_CLICK_BLOCK && ShiftPath.isItemWand(itemInHand)) {
            player.sendMessage("Debug: Right clicked a block with a wand!");
        }
    }
}
