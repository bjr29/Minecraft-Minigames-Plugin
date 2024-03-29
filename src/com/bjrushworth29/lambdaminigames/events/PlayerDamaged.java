package com.bjrushworth29.lambdaminigames.events;

import com.bjrushworth29.lambdaminigames.games.util.Game;
import com.bjrushworth29.lambdaminigames.managers.GameManager;
import com.bjrushworth29.lambdaminigames.managers.PlayerConstraintManager;
import com.bjrushworth29.lambdaminigames.utils.Debug;
import com.bjrushworth29.lambdaminigames.utils.PlayerConstraints;
import com.bjrushworth29.lambdaminigames.utils.PlayerGameData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamaged implements Listener {
	@EventHandler
	private void handler(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();

		PlayerConstraints appliedConstraints = PlayerConstraintManager.getAppliedConstraints(player);

		if (!(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && appliedConstraints.pvp())) {
			if (!appliedConstraints.takesAnyDamage() ||
					(!appliedConstraints.takesFallDamage() && event.getCause() == EntityDamageEvent.DamageCause.FALL)
			) {
				event.setCancelled(true);
			}
		}

		Game game = GameManager.getPlayerGame(player);

		if (game == null) {
			return;
		}

		PlayerGameData gameConstraints = game.getPlayerGameConstraints(player);

		if (gameConstraints.hasSpawnProtection()) {
			event.setCancelled(true);
			return;
		}

		if (player.getHealth() - event.getFinalDamage() <= 0) {
			event.setCancelled(true);

			game.handleDeathOrLeave(player, false);
		}
	}

	@EventHandler
	private void handler(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();

		PlayerConstraints playerConstraints = PlayerConstraintManager.getAppliedConstraints(player);

		if (!(playerConstraints.pvp() && PlayerConstraintManager.getAppliedConstraints(damager).pvp())) {
			event.setCancelled(true);
			return;
		}

		if (!playerConstraints.takesAnyDamage()) {
			player.setHealth(player.getMaxHealth());
			Debug.info("Disabled damage for '%s'", player);
		}

		Game game = GameManager.getPlayerGame(player);

		if (game != GameManager.getPlayerGame(damager)) {
			Debug.warn("Players '%s' and '%s' attempted to attack one another but are in separate games", player, damager);

			return;
		}

		if (game == null) {
			return;
		}

		PlayerGameData playerGameConstraints = game.getPlayerGameConstraints(player);
		PlayerGameData damagerGameConstraints = game.getPlayerGameConstraints(damager);

		if (playerGameConstraints.hasSpawnProtection()) {
			event.setCancelled(true);
			return;

		} else if (damagerGameConstraints.hasSpawnProtection()) {
			damagerGameConstraints.removeSpawnProtection();
		}

		if (player.getHealth() - event.getFinalDamage() <= 0) {
			event.setCancelled(true);
			game.handleDeathOrLeave(player, false);
		}

		playerGameConstraints.setApplyKnockback(true);
		playerGameConstraints.setPreviousAttacker(damager);
	}
}
