package net.lectusHUB.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import net.lectusHUB.MainLectusHub;

public class EventsManager {
	
	public MainLectusHub pl;
	
	public EventsManager(MainLectusHub MainLectusHub) {
		this.pl = MainLectusHub;
	}
	
	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new InventoryListener(), pl);
		pm.registerEvents(new PlayerEvents(), pl);
		pm.registerEvents(new JoinEvent(), pl);
		pm.registerEvents(new DoubleJumpEvent(), pl);
		pm.registerEvents(new WeatherEvent() , pl);
	}
	
}
