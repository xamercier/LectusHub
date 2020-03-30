package net.lectusHUB.inventory.menus;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.lectusAPI.MainLectusApi;
import net.lectusAPI.GameManager.GameManager;
import net.lectusAPI.utils.ItemStackUtils;
import net.lectusAPI.utils.LectusInventory;
import net.lectusAPI.utils.LectusItem;
import net.lectusAPI.utils.ServerUtils;
import net.lectusHUB.inventory.InventoryManager;

public class StaffMenu extends LectusInventory {

	public StaffMenu() {
		super(ChatColor.GOLD + "Menu staff", 5 * 9);
		buildInventory();
	}

	@Override
	public void buildInventory() {
		ArrayList<GameManager> games = new ArrayList<GameManager>();
		for (GameManager game : GameManager.values()) {
			if (game.isAGame()) {
				games.add(game);
			}
		}

		int position = 0;
		int settedGames = 0;

		for (GameManager game : games) {
			ArrayList<String> Servers = MainLectusApi.getInstance().getSql().getServers(game.getGamePrefix());
			for (String serv : Servers) {
				LectusItem item = game.getGameItem();
				String state = MainLectusApi.getInstance().getSql().getState(Integer.parseInt(serv));
				if (state.equalsIgnoreCase("RUNNING")) {
					item.setName(ChatColor.RED + game.getServerPrefix() + serv);
					item.setLore(ChatColor.RED + "Status: En cours");
					setItem(position, item);
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
			setItem(0, item);
		}
	}

	@Override
	public void onClick(Player player, ItemStack item) {
		if (!item.getData().getItemType().equals(Material.AIR)) {
			for (GameManager game : GameManager.values()) {
				if (item.getItemMeta().getDisplayName().contains(game.getGamePrefix())) {
					ServerUtils.sendPlayerToServer(player, item.getItemMeta().getDisplayName().replace(ChatColor.RED + "", ""));
				}
			}
		}
	}

	@Override
	public ItemStack getRepresenter() {
		return InventoryManager.getInstance().staffMenu.getItemStack();
	}
}
