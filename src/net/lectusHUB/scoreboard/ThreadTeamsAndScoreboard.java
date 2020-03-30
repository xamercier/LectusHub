package net.lectusHUB.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import net.lectusAPI.cache.PlayerCache;
import net.lectusAPI.utils.TeamUtils;
import net.lectusHUB.MainLectusHub;

public class ThreadTeamsAndScoreboard extends Thread {

	public void run() {
		while (true) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (MainLectusHub.getInstance().boardsPlayers.containsKey(p)) {
					if (!TeamUtils.getInstance().containsPlayerInTeam(p,
							PlayerCache.getCacheByPlayer(p).getRank().getName())) {
						Team oldTeam = TeamUtils.getInstance().getTeamOfPlayer(p);
						TeamUtils.getInstance().removePlayerOfTeam(p, oldTeam.getName());
						TeamUtils.getInstance().addPlayerToTeam(p, PlayerCache.getCacheByPlayer(p).getRank().getName());
					}
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(1, "  ");
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(2, "§7Pseudo: §e" + p.getName());
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(3,
								"§7Grade: " + PlayerCache.getCacheByPlayer(p).getRank().getDisplayName().toString());
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(4,
								"§7Coins: §e " + PlayerCache.getCacheByPlayer(p).getCoins());
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(5, " ");
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(6,
								"§7Joueurs: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(7, "§7Hub: §d#" + MainLectusHub.getInstance().getHubId());
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(8, " ");
						MainLectusHub.getInstance().boardsPlayers.get(p).setLine(9, "    §6play.lectus.ca");
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
