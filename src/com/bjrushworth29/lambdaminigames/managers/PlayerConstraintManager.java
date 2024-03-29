package com.bjrushworth29.lambdaminigames.managers;

import com.bjrushworth29.lambdaminigames.enums.Constraints;
import com.bjrushworth29.lambdaminigames.utils.Debug;
import com.bjrushworth29.lambdaminigames.utils.PlayerConstraints;
import com.bjrushworth29.lambdaminigames.enums.DebugLevel;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerConstraintManager {
	private static final HashMap<String, PlayerConstraints> CONSTRAINTS = new HashMap<>();
	private static final HashMap<Player, String> APPLIED_CONSTRAINTS = new HashMap<>();

	static {
		CONSTRAINTS.put(Constraints.HUB.toString(), new PlayerConstraints(
				false,
				GameMode.ADVENTURE,
				true,
				false,
				true,
				false,
				false,
				false,
				false
		));

		CONSTRAINTS.put(Constraints.WAITING.toString(), new PlayerConstraints(
				false,
				GameMode.ADVENTURE,
				false,
				false,
				false,
				false,
				false,
				false,
				false
		));

		CONSTRAINTS.put(Constraints.SPECTATOR.toString(), new PlayerConstraints(
				false,
				GameMode.SPECTATOR,
				true,
				false,
				false,
				false,
				false,
				false,
				false
		));

		CONSTRAINTS.put(Constraints.PVP_DEFAULT.toString(), new PlayerConstraints(
				true,
				GameMode.SURVIVAL,
				true,
				false,
				true,
				true,
				false,
				false,
				false
		));

		CONSTRAINTS.put(Constraints.PVP_SUMO.toString(), new PlayerConstraints(
				true,
				GameMode.SURVIVAL,
				true,
				false,
				false,
				false,
				false,
				false,
				false
		));

		Debug.info(DebugLevel.MIN, "Initialised constraints");
	}

	public static PlayerConstraints getAppliedConstraints(Player player) {
		return CONSTRAINTS.get(
				APPLIED_CONSTRAINTS.get(player)
		);
	}

	public static void applyConstraints(Player player, Constraints constraints) {
		applyConstraints(player, constraints.toString());
	}

	public static void applyConstraints(Player player, String constraintName) {
		APPLIED_CONSTRAINTS.put(player, constraintName);

		PlayerConstraints constraints = CONSTRAINTS.get(constraintName);

		player.setGameMode(constraints.gameMode());
		player.setWalkSpeed(constraints.canMove() ? 0.2f : 0);

		Debug.info(DebugLevel.FULL, "Given '%s' state '%s'", player.getName(), constraintName);
	}

	public static void clearPlayer(Player player) {
		APPLIED_CONSTRAINTS.remove(player);
	}
}
