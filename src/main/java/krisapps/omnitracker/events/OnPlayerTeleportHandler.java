package krisapps.omnitracker.events;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.managers.ActivityLogManager;
import krisapps.omnitracker.enums.OperationType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.UUID;

public class OnPlayerTeleportHandler implements Listener {

    OmniTracker main;
    public OnPlayerTeleportHandler(OmniTracker main){
        this.main = main;
    }

    @EventHandler
    void OnTeleport(PlayerTeleportEvent teleportEvent){

        ActivityLogManager activityLogManager = new ActivityLogManager(main);

        final Player player = teleportEvent.getPlayer();
        final String from = "X: " + teleportEvent.getFrom().getX() + " Y: " + teleportEvent.getFrom().getY() + " Z: " + teleportEvent.getFrom().getZ();
        final String to = "X: " + teleportEvent.getTo().getX() + " Y: " + teleportEvent.getTo().getY() + " Z: " + teleportEvent.getTo().getZ();
        final UUID playerUUID = teleportEvent.getPlayer().getUniqueId();
        HashMap<String, String> args = new HashMap<>();
        args.put("arg1", from);
        args.put("arg2", to);

        activityLogManager.logOperation(player.getName(), playerUUID.toString(), OperationType.TELEPORT, args);
    }
}
