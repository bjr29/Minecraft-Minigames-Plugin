package com.bjrushworth29.managers;

import com.bjrushworth29.enums.Constraints;
import com.bjrushworth29.utils.Debug;
import com.bjrushworth29.utils.PlayerConstraints;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerConstraintManager {
	private static final HashMap<String, PlayerConstraints> CONSTRAINTS = new HashMap<>();
	private static final HashMap<String, String> APPLIED_CONSTRAINTS = new HashMap<>();

	static {
		CONSTRAINTS.put(Constraints.HUB.toString(), new PlayerConstraints(
				false,
				GameMode.ADVENTURE,
				true,
				false,
				true,
				true,
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
				false
		));

		CONSTRAINTS.put(Constraints.PVP_DEFAULT.toString(), new PlayerConstraints(
				true,
				GameMode.ADVENTURE,
				true,
				true,
				true,
				true,
				false,
				false
		));

		CONSTRAINTS.put(Constraints.PVP_SUMO.toString(), new PlayerConstraints(
				true,
				GameMode.ADVENTURE,
				true,
				false,
				false,
				false,
				false,
				false
		));

		Debug.info("Initialised constraints");
	}

	public static PlayerConstraints getAppliedConstraints(Player player) {
		return CONSTRAINTS.get(
				APPLIED_CONSTRAINTS.get(player.getName())
		);
	}

	public static void applyConstraints(Player player, Constraints constraints) {
		applyConstraints(player, constraints.toString());
	}

	public static void applyConstraints(Player player, String constraintName) {
		APPLIED_CONSTRAINTS.put(player.getName(), constraintName);

		PlayerConstraints constraints = CONSTRAINTS.get(constraintName);

		player.setGameMode(constraints.gameMode());
		player.setWalkSpeed(constraints.canMove() ? 0.2f : 0);

		Debug.info("Given '%s' state '%s'", player.getName(), constraintName);
	}

	public static void clearPlayer(Player player) {
		APPLIED_CONSTRAINTS.remove(player.getName());
	}
}
