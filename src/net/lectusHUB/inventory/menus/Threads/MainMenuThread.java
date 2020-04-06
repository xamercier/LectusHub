package net.lectusHUB.inventory.menus.Threads;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import net.lectusAPI.MainLectusApi;
import net.lectusAPI.GameManager.GameManager;
import net.lectusAPI.utils.ItemStackUtils;
import net.lectusAPI.utils.LectusInventory;
import net.lectusAPI.utils.LectusItem;

public class MainMenuThread extends Thread {

	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (LectusInventory li : LectusInventory.inventorries) {
				if (li.getTitle().contains("Menu des jeux")) {
					li.getInventory().clear();
					
					

					ArrayList<String> lore = new ArrayList<String>();
					lore.clear();
					LectusItem GlassPanel = new LectusItem(
							ItemStackUtils.create(Material.STAINED_GLASS_PANE, (byte) 15, 1, "", lore));
					/*
					 * //LIGNE EN HAUT setItem(0, GlassPanel); setItem(1, GlassPanel);
					 * setItem(2, GlassPanel); setItem(3, GlassPanel); setItem(4,
					 * GlassPanel); setItem(5, GlassPanel); setItem(6, GlassPanel);
					 * setItem(7, GlassPanel); setItem(8, GlassPanel); //LIGNE GAUCHE
					 * setItem(9, GlassPanel); setItem(18, GlassPanel); setItem(27,
					 * GlassPanel); setItem(36, GlassPanel); //LIGNE DROITE setItem(17,
					 * GlassPanel); setItem(26, GlassPanel); setItem(35, GlassPanel);
					 * setItem(44, GlassPanel); //LIGNE EN BAS setItem(45, GlassPanel);
					 * setItem(46, GlassPanel); setItem(47, GlassPanel); setItem(48,
					 * GlassPanel); setItem(49, GlassPanel); setItem(50, GlassPanel);
					 * setItem(51, GlassPanel); setItem(52, GlassPanel); setItem(53,
					 * GlassPanel);
					 */

					// TROISIEME LIGNE
					li.setItem(18, GlassPanel);
					li.setItem(19, GlassPanel);
					li.setItem(20, GlassPanel);
					li.setItem(21, GlassPanel);
					li.setItem(22, GlassPanel);
					li.setItem(23, GlassPanel);
					li.setItem(24, GlassPanel);
					li.setItem(25, GlassPanel);
					li.setItem(26, GlassPanel);

					ArrayList<GameManager> games = new ArrayList<GameManager>();

					for (GameManager game : GameManager.values()) {
						if (game.isAGame()) {
							games.add(game);
						}
					}

					int position = 12;

					for (GameManager game : games) {
						LectusItem item = game.getGameItem();

						int playersOnline = 0;

						ArrayList<String> serverList = MainLectusApi.getInstance().getSql().getServers(game.getGamePrefix());
						for (String srv : serverList) {
							playersOnline = playersOnline + MainLectusApi.getInstance().getSql().getPlayers(Integer.parseInt(srv));
						}
						item.setLore(game.getItemLore(), ChatColor.GOLD + "Joueurs en ligne: " + playersOnline);
						li.setItem(position, item);
						position = position + 2;
					}

					/*
					 * fuck that hardcoded shit ;) lore.add(ChatColor.GOLD +
					 * "Developpeur: xamercier"); LectusItem HikaBrain1v1 = new
					 * LectusItem(ItemStackUtils.create(Material.BED, (byte) 0, 1,
					 * ChatColor.RED + "HikaBrain1v1", lore)); setItem(12, HikaBrain1v1);
					 * 
					 * lore.clear(); lore.add(ChatColor.GOLD + "Developpeur: xamercier");
					 * LectusItem HikaBrain2v2 = new
					 * LectusItem(ItemStackUtils.create(Material.BED, (byte) 0, 1,
					 * ChatColor.RED + "HikaBrain2v2", lore)); setItem(14, HikaBrain2v2);
					 */

					
					
					
					
					
					
				}
			}

		}
	}

}
