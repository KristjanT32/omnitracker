package krisapps.omnitracker.events;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.managers.ActivityLogManager;
import krisapps.omnitracker.enums.OperationType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerJoinHandler implements Listener {

    OmniTracker main;
    public PlayerJoinHandler(OmniTracker main){
        this.main = main;
    }

    @EventHandler
    void OnPlayerJoin(PlayerJoinEvent e){

        final Player player = e.getPlayer();
        ActivityLogManager activityLogManager = new ActivityLogManager(main);

        HashMap<String, String> args = new HashMap<>();
        if (player.getAddress().getAddress() != null) {
            args.put("arg2", player.getAddress().getAddress().toString());
        }else{
            args.put("arg2", "Unknown IP address");
        }
        activityLogManager.logOperation(player.getName(), player.getUniqueId().toString(), OperationType.JOIN_SERVER, args);
    }
}
