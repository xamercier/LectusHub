package net.lectusHUB.inventory.menus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.lectusAPI.utils.ItemStackUtils;
import net.lectusAPI.utils.LectusInventory;
import net.lectusAPI.utils.LectusItem;

public class PlayerMenu extends LectusInventory {
	
	private Player player;
	
	public PlayerMenu(Player player) {
		super(ChatColor.GOLD + "Menu de " + player.getName(), 5 * 9);
		this.player = player;
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
		LectusItem head = new LectusItem(ItemStackUtils.getOwnedHead(this.player));
		head.setName(ChatColor.GREEN + this.player.getName());
		return head.getItemStack();
	}
}
