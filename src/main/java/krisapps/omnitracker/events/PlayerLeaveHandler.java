package krisapps.omnitracker.events;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.managers.ActivityLogManager;
import krisapps.omnitracker.enums.OperationType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerLeaveHandler implements Listener {

    OmniTracker main;
    public PlayerLeaveHandler(OmniTracker main){
        this.main = main;
    }

    @EventHandler
    void OnPlayerLeave(PlayerQuitEvent quitEvent){

        ActivityLogManager activityLogManager = new ActivityLogManager(main);

        final Player player = quitEvent.getPlayer();
        final String quitMessage = quitEvent.getQuitMessage();
        HashMap<String, String> args = new HashMap<>();
        args.put("arg1", quitMessage);

        activityLogManager.logOperation(player.getName(), player.getUniqueId().toString(), OperationType.LEAVE_SERVER, args);
    }
}
