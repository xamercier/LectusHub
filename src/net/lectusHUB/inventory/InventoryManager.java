package net.lectusHUB.inventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import net.lectusAPI.utils.LectusItem;
import net.lectusHUB.inventory.menus.FriendsGroupsMenu;
import net.lectusHUB.inventory.menus.MainMenu;
import net.lectusHUB.inventory.menus.ShopMenu;
import net.lectusHUB.inventory.menus.StaffMenu;

public class InventoryManager {
	
	private static InventoryManager instance = null;
	
	public LectusItem gamesMenu;
	public LectusItem boutiqueMenu;
	public LectusItem playerMenu;
	public LectusItem staffMenu;
	public LectusItem friendsGroupsMenu;
	
	public InventoryManager() { 
		createItems();
		registerInvs();
	}
	
	public void createItems() {
		this.gamesMenu = new LectusItem(Material.COMPASS, ChatColor.GOLD + "Menu des jeux");
		this.boutiqueMenu = new LectusItem(Material.GOLD_INGOT, ChatColor.YELLOW + "Boutique (Prochainement)");
		this.staffMenu = new LectusItem(Material.WATCH, ChatColor.GRAY + "Menu du staff");
		this.friendsGroupsMenu = new LectusItem(Material.RAW_FISH, ChatColor.GOLD + "Amis & groupes (Prochainement)", 1, (short) 2);
	}
	
	public void registerInvs() {
		new MainMenu();
		new ShopMenu();
		new StaffMenu();
		new FriendsGroupsMenu();
	}
	
	public static InventoryManager getInstance() {
		if (instance == null) {
			instance = new InventoryManager();
		}
		return instance;
	}
	
}
