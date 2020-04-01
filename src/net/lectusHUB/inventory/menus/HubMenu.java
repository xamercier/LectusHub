package net.lectusHUB.inventory.menus;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.lectusAPI.MainLectusApi;
import net.lectusAPI.GameManager.GameManager;
import net.lectusAPI.utils.LectusInventory;
import net.lectusAPI.utils.LectusItem;
import net.lectusAPI.utils.ServerUtils;
import net.lectusHUB.inventory.InventoryManager;

public class HubMenu extends LectusInventory {

	public HubMenu() {
		super(ChatColor.GOLD + "Menu des hubs", 5 * 9);
		buildInventory();
	}

	@SuppressWarnings("unused")
	@Override
	public void buildInventory() {
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

				if (state.equalsIgnoreCase("BOOTING")) {
					item.setLore(ChatColor.RED + "Ce Hub est en cour de redemarrage!");
				} else {
					item.setLore(ChatColor.RED + "Joueurs: "
							+ MainLectusApi.getInstance().getSql().getPlayers(Integer.parseInt(serv)) + "/20");
				}

				setItem(position, item);
				position++;
				settedGames++;
			}
		}
	}

	@Override
	public void onClick(Player player, ItemStack item) {
		if (!item.getData().getItemType().equals(Material.AIR)) {
			for (GameManager game : GameManager.values()) {
				if (item.getItemMeta().getDisplayName().contains(game.getGamePrefix())) {
					ServerUtils.sendPlayerToServer(player,
							item.getItemMeta().getDisplayName().replace(ChatColor.GREEN + "", ""));
				}
			}
		}
	}

	@Override
	public ItemStack getRepresenter() {
		return InventoryManager.getInstance().HubMenu.getItemStack();
	}
}