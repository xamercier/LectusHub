package net.lectusHUB.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import net.lectusAPI.grade.Rank;

public class DoubleJumpEvent implements Listener {

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (Rank.ALPHA.hasPermission(player)) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            } else if (player.isFlying()) {
                return;
            } else {
                event.setCancelled(true);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Rank.ALPHA.hasPermission(player)) {
            if ((player.getGameMode() != GameMode.CREATIVE)
                    && (player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR)
                    && (!player.isFlying())) {
                player.setAllowFlight(true);
            }
        }
    }

}