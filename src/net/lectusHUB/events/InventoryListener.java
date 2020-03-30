package net.lectusHUB.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.lectusAPI.utils.LectusInventory;
import net.lectusHUB.MainLectusHub;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() != null) {
			for (LectusInventory li : LectusInventory.inventorries) {
				if (li.getRepresenter().equals(event.getItem())) {
					li.openInvetory(event.getPlayer());
				}
			}
			if (event.getItem().getItemMeta().getDisplayName().contains(event.getPlayer().getName())) {
				MainLectusHub.getInstance().PlayerMenu.get(event.getPlayer()).openInvetory(event.getPlayer());
			}
			event.setCancelled(true);

		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getCurrentItem() != null) {
			for (LectusInventory li : LectusInventory.inventorries) {
				if (li.getInventory().equals(event.getClickedInventory())) {
					li.onClick((Player) event.getWhoClicked(), event.getCurrentItem());
					event.setCancelled(true);
				}
			}
		}
		event.setCancelled(true);
	}
}
