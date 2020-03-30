package net.lectusHUB;



import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.lectusAPI.grade.Rank;
import net.lectusAPI.utils.ServerUtils;

public class CmdBuild implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Rank.BUILDEUR.hasPermission(p)) {
				ServerUtils.sendPlayerToServer(p, "BuildServ");
			} else {
				p.sendMessage(ChatColor.RED + "Erreur: Tu n'a pas la permission (rank.buildeur) de faire cette commande!");
			}
		}
		return true;
	}
}