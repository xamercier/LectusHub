package net.lectusHUB.inventory.menus.Threads;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import net.lectusAPI.MainLectusApi;
import net.lectusAPI.GameManager.GameManager;
import net.lectusAPI.utils.LectusInventory;
import net.lectusAPI.utils.LectusItem;

public class HubMenuThread extends Thread {

	@SuppressWarnings("unused")
	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (LectusInventory li : LectusInventory.inventorries) {
				if (li.getTitle().contains("hubs")) {
					li.getInventory().clear();
					ArrayList<GameManager> hubs = new ArrayList<GameManager>();
					for (GameManager hub : GameManager.values()) {
						if (!hub.isAGame()) {
							hubs.add(hub);
						}
					}

					int position = 0;
					int settedGames = 0;

					for (GameManager hub : hubs) {
						ArrayList<String> Servers = MainLectusApi.getInstance().getSql().getServers(hub.getGamePrefix());
						for (String serv : Servers) {
							LectusItem item = hub.getGameItem();
							String state = MainLectusApi.getInstance().getSql().getState(Integer.parseInt(serv));
								item.setName(ChatColor.GREEN + hub.getServerPrefix() + serv);
								item.setLore(ChatColor.RED + "Clique pour rejoindre ce Hub!");
								li.setItem(position, item);
								position++;
								settedGames++;
						}
					}
				}
			}

		}
	}

}
