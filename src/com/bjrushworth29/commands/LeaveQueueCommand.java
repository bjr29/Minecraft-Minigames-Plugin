package com.bjrushworth29.commands;

import com.bjrushworth29.managers.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveQueueCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandString, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(ChatColor.RED + "Must be a player to use this command.");

			return true;
		}

		GameManager.leaveQueue(player);

		return true;
	}
}
