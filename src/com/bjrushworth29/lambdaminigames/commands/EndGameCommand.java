package com.bjrushworth29.lambdaminigames.commands;

import com.bjrushworth29.lambdaminigames.games.util.Game;
import com.bjrushworth29.lambdaminigames.managers.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndGameCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandString, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Must be a player to use this command.");

			return true;
		}

		Player player = (Player) sender;
		Game game = GameManager.getPlayerGame(player);

		if (game == null) {
			player.sendMessage(ChatColor.RED + "That player isn't in a game");
			return true;
		}

		game.end(player);

		return true;
	}
}
