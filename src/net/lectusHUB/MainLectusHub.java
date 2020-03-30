package net.lectusHUB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.lectusAPI.grade.Rank;
import net.lectusAPI.utils.ScoreboardSign;
import net.lectusAPI.utils.TeamUtils;
import net.lectusAPI.utils.TitleManager;
import net.lectusHUB.events.EventsManager;
import net.lectusHUB.inventory.menus.PlayerMenu;
import net.lectusHUB.inventory.menus.Threads.StaffMenuThread;
import net.lectusHUB.scoreboard.ThreadTeamsAndScoreboard;
import net.lectusHUB.thread.SignThread;

public class MainLectusHub extends JavaPlugin {

	public static MainLectusHub instance;
	
	public List<Player>					mod					= new ArrayList<>();
	
	public  int hubId = 0;

	public  int getHubId() {
		return hubId;
	}

	public  void setHubId(int hubId) {
		MainLectusHub.getInstance().hubId = hubId;
	}

	public static MainLectusHub getInstance() {
		return instance;
	}

	Thread TeamsAndScoreboard = new Thread(new ThreadTeamsAndScoreboard());
	
	// SCOREBOARD
	public Map<Player, ScoreboardSign> boardsPlayers = new HashMap<>();
	// SCOREBOARD
	
	public Map<Player, PlayerMenu> PlayerMenu = new HashMap<>();

	@SuppressWarnings({ "deprecation"})
	public void onEnable() {
		super.onEnable();
		instance = this;
		new EventsManager(this).registerEvents();
		startActionBarTask();
		for (Player pls : Bukkit.getOnlinePlayers()) {
			new PlayerMenu(pls);
		}
		
		
		// SCOREBOARD
		for (Rank rank : Rank.values()) {
			TeamUtils.getInstance().createTeam(rank.getName(), rank.getShortName() + " ");
		}
		
		TeamsAndScoreboard.start();
		// SCOREBOARD
		
		this.hubId = this.getServer().getPort();
		 
		
		getCommand("build").setExecutor(new CmdBuild());
		
		StaffMenuThread ThreadStaffMenu = new StaffMenuThread();
		ThreadStaffMenu.start();
		
		for (World world : Bukkit.getWorlds()) {
			String name = world.getName();
			getServer().getWorld(name).setStorm(false);
			getServer().getWorld(name).getEntities().clear();
			getServer().getWorld(name).setTime(0);
	          for(Entity en : getServer().getWorld(name).getEntities()){
	              if(!(en instanceof Player)) {
	              en.remove();
	              }
	          }
		}
		
		Bukkit.getScheduler().runTaskTimer(this, new BukkitRunnable() {
			@Override
			public void run() {
				SignThread.signReload();
			}
		}, 0, 40);
	}

	public void startActionBarTask() {
		ArrayList<String> messages = new ArrayList<>();
		messages.add(ChatColor.YELLOW + "Environement: " + ChatColor.AQUA + "Developpement");
		/*
		messages.add(ChatColor.RED + "Bon amusement sur " + ChatColor.GREEN + "Lectus" + ChatColor.RED + " !");
		messages.add(ChatColor.GOLD + "Les jeux disponibles: " + ChatColor.YELLOW + "HikaBrain1v1 " + ChatColor.YELLOW + "HikaBrain2v2");
		messages.add(ChatColor.GOLD + "Les jeux prochainement disponibles: " + ChatColor.YELLOW + "SkyWars");
				*/
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			int currentIndex = 0;
			@Override
			public void run() {
				String currentMessage = messages.get(currentIndex);
				currentIndex++;
				if (currentIndex >= messages.size()) {
					currentIndex = 0;
				}
				Bukkit.getOnlinePlayers().forEach(player -> {
					TitleManager.sendActionBar(player, currentMessage);
				});
			}
		}, 60L, 60L);
	}

	@SuppressWarnings("deprecation")
	public void onDisable() {
		super.onDisable();
		TeamsAndScoreboard.stop();
		for (Rank rank : Rank.values()) {
			TeamUtils.getInstance().deleteTeam(rank.getName());
		}
	}
}