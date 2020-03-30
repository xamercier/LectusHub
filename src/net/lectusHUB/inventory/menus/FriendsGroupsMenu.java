package net.lectusHUB.inventory.menus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.lectusAPI.utils.LectusInventory;
import net.lectusHUB.inventory.InventoryManager;

public class FriendsGroupsMenu extends LectusInventory {
	
	public FriendsGroupsMenu() {
		super(ChatColor.GOLD + "Amis et Groupes", 5 * 9);
		buildInventory();
	}
	
	@Override
	public void buildInventory() {
		
	}
	
	@Override
	public void onClick(Player player, ItemStack item) {
	}
	
	@Override
	public ItemStack getRepresenter() {
		return InventoryManager.getInstance().friendsGroupsMenu.getItemStack();
	}
}
