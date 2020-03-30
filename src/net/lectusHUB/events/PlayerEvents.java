package net.lectusHUB.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.PortalCreateEvent;

import net.lectusAPI.cache.PlayerCache;
import net.lectusAPI.grade.Rank;
import net.md_5.bungee.api.ChatColor;

public class PlayerEvents implements Listener {

	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void place(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Rank playerRank = PlayerCache.getCacheByPlayer(p).getRank();
		if (playerRank != Rank.ADMIN && playerRank != Rank.BUILDEUR && playerRank != Rank.OWNER) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage("");
		e.getDrops().clear();
		Player p = e.getEntity();
		p.resetMaxHealth();
		p.kickPlayer(ChatColor.RED + "Erreur du chargement de votre profil, veuillez vous reconnecter.");
	}

	@EventHandler
	public void breake(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Rank playerRank = PlayerCache.getCacheByPlayer(p).getRank();
		if (playerRank != Rank.ADMIN && playerRank != Rank.BUILDEUR && playerRank != Rank.OWNER) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getLocation().getY() < 100) {
			p.teleport(Bukkit.getWorld("world").getSpawnLocation());
		}
	}

	@EventHandler
	public void weather(WeatherChangeEvent ev) {
		ev.setCancelled(true);
	}

	@EventHandler
	public void damage(EntityDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void portalCreate(PortalCreateEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void foodLevel(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}

	@EventHandler
	public void spawnMob(CreatureSpawnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void drop(PlayerDropItemEvent ev) {
		ev.setCancelled(true);
	}

}