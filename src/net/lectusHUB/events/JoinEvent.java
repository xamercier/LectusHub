package net.lectusHUB.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.lectusAPI.MainLectusApi;
import net.lectusAPI.cache.PlayerCache;
import net.lectusAPI.grade.Rank;
import net.lectusAPI.utils.LectusInventory;
import net.lectusAPI.utils.ScoreboardSign;
import net.lectusAPI.utils.TeamUtils;
import net.lectusAPI.utils.TitleManager;
import net.lectusAPI.utils.VanishUtils;
import net.lectusHUB.MainLectusHub;
import net.lectusHUB.inventory.InventoryManager;
import net.lectusHUB.inventory.menus.PlayerMenu;

public class JoinEvent implements Listener {

	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(null);
		VanishUtils.getInstance().checkVanish(p);
		VanishUtils.getInstance().disableVanish(p);
		if (MainLectusApi.getInstance().getSql().hasModMode(p)) {
			MainLectusHub.getInstance().mod.add(p);
		}
		if (!MainLectusHub.getInstance().mod.contains(p)) {
			for (Player playerToHide : MainLectusHub.getInstance().mod) {
				VanishUtils.getInstance().enableVanish(playerToHide);
			}
		}
		if (MainLectusApi.getInstance().getSql().hasModMode(p)) {
			e.setJoinMessage(null);
			VanishUtils.getInstance().enableVanish(p);
			p.getInventory().clear();
		}
			Bukkit.getScheduler().runTaskLater(MainLectusHub.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (Rank.VIP.hasPermission(p) && !MainLectusHub.getInstance().mod.contains(p)) {
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "+ " + PlayerCache.getCacheByPlayer(p).getRank().getDisplayName()
										+ " " + p.getDisplayName() + ChatColor.GRAY + " a rejoint le hub !");
					}
					TitleManager.sendTitle(p, ChatColor.RED + "Bienvenue sur Lectus !", ChatColor.GREEN + "Bon jeux !",
							(int) 20L);
					p.setFoodLevel(20);
					PlayerMenu playerMenu = new PlayerMenu(p);
					MainLectusHub.getInstance().PlayerMenu.put(p, playerMenu);
					if (!LectusInventory.inventorries.contains(playerMenu)) {
						LectusInventory.inventorries.add(playerMenu);
					}
					p.getInventory().clear();
					p.getInventory().setItem(0, InventoryManager.getInstance().gamesMenu.getItemStack());
					p.getInventory().setItem(2, InventoryManager.getInstance().HubMenu.getItemStack());
					//p.getInventory().setItem(2, InventoryManager.getInstance().boutiqueMenu.getItemStack());
					p.getInventory().setItem(4, playerMenu.getRepresenter());
					if (Rank.BUILDEUR.hasPermission(p)) {
						p.getInventory().setItem(6, InventoryManager.getInstance().staffMenu.getItemStack());
					}
					p.getInventory().setItem(8, InventoryManager.getInstance().friendsGroupsMenu.getItemStack());

					// SCOREBOARD and team

					TeamUtils.getInstance().addPlayerToTeam(p, PlayerCache.getCacheByPlayer(p).getRank().getName());

					ScoreboardSign scoreboard = new ScoreboardSign(p, ChatColor.YELLOW + "play.lectus.ca");
					scoreboard.create();
					scoreboard.setLine(1, "  ");
					scoreboard.setLine(2, "§7Pseudo: §e" + p.getName());
					scoreboard.setLine(3,
							"§7Grade: " + PlayerCache.getCacheByPlayer(p).getRank().getDisplayName().toString());
					scoreboard.setLine(4, "§7Coins: §e " + PlayerCache.getCacheByPlayer(p).getCoins());
					scoreboard.setLine(5, " ");
					scoreboard.setLine(6,
							"§7Joueurs: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
					scoreboard.setLine(7, "§7Hub: §d#" + MainLectusHub.getInstance().getHubId());
					scoreboard.setLine(8, " ");
					scoreboard.setLine(9, "    §6play.lectus.ca");
					MainLectusHub.getInstance().boardsPlayers.put(p, scoreboard);
				}
			}, 1 * 3);

		p.setGameMode(GameMode.ADVENTURE);
		p.teleport(Bukkit.getWorld("world").getSpawnLocation());
	}

	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		// SCOREBOARD and team
		if (MainLectusHub.getInstance().boardsPlayers.containsKey(p)) {
			MainLectusHub.getInstance().boardsPlayers.remove(p);
		}
		TeamUtils.getInstance().removePlayerOfTeam(p, PlayerCache.getCacheByPlayer(p).getRank().getName());
		// SCOREBOARD and team
		if (MainLectusHub.getInstance().mod.contains(p)) {
			e.setQuitMessage(null);
			MainLectusHub.getInstance().mod.remove(p);
			VanishUtils.getInstance().disableVanish(p);
		}
		e.setQuitMessage(null);
	}

}
