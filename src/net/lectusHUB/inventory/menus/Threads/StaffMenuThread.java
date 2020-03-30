package net.lectusHUB.inventory.menus.Threads;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import net.lectusAPI.MainLectusApi;
import net.lectusAPI.GameManager.GameManager;
import net.lectusAPI.utils.ItemStackUtils;
import net.lectusAPI.utils.LectusInventory;
import net.lectusAPI.utils.LectusItem;

public class StaffMenuThread extends Thread {

	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (LectusInventory li : LectusInventory.inventorries) {
				if (li.getTitle().contains("staff")) {
					li.getInventory().clear();
					ArrayList<GameManager> games = new ArrayList<GameManager>();
					for (GameManager game : GameManager.values()) {
						if (game.isAGame()) {
							games.add(game);
						}
					}

					int position = 0;
					int settedGames = 0;

					for (GameManager game : games) {
						ArrayList<String> Servers = MainLectusApi.getInstance().getSql()
								.getServers(game.getGamePrefix());
						for (String serv : Servers) {
							LectusItem item = game.getGameItem();
							String state = MainLectusApi.getInstance().getSql().getState(Integer.parseInt(serv));
							if (state.equalsIgnoreCase("RUNNING")) {
								item.setName(ChatColor.RED + game.getServerPrefix() + serv);
								item.setLore(ChatColor.RED + "Status: En cour");
								li.setItem(position, item);
								position++;
								settedGames++;
							}
						}
					}
					if (settedGames == 0) {
						ArrayList<String> lore = new ArrayList<String>();
						lore.add(ChatColor.GOLD + "Il n'y as aucune partie lancée pour le moment.");
						LectusItem item = new LectusItem(ItemStackUtils.create(Material.HARD_CLAY, (byte) 14, 1,
								ChatColor.RED + "Aucune partie lancée", lore));
						li.setItem(0, item);
					}
				}
			}

		}
	}

}
