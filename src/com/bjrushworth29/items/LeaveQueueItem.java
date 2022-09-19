package com.bjrushworth29.items;

import com.bjrushworth29.managers.GameManager;
import com.bjrushworth29.managers.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LeaveQueueItem implements Listener {
	@EventHandler
	private void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		if (item == null || !ItemManager.equalMeta(item, ItemManager.getItem("leaveQueue"))) {
			return;
		}

		GameManager.leaveQueue(player);
	}
}
