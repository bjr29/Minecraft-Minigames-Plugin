package com.bjrushworth29.lambdaminigames.events;

import com.bjrushworth29.lambdaminigames.managers.GameManager;
import com.bjrushworth29.lambdaminigames.managers.PlayerConstraintManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeftServer implements Listener {
	@EventHandler
	private void handler(PlayerQuitEvent event) {
		event.setQuitMessage("");

		Player player = event.getPlayer();

		PlayerConstraintManager.clearPlayer(player);

		GameManager.removePlayer(player);
	}
}
