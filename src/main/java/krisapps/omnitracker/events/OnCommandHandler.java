package krisapps.omnitracker.events;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.managers.ActivityLogManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;

public class OnCommandHandler implements Listener {

    OmniTracker main;
    public OnCommandHandler(OmniTracker main){
        this.main = main;
    }

    @EventHandler
    void OnCommandSent(PlayerCommandPreprocessEvent preprocessEvent){

        ActivityLogManager activityLogManager = new ActivityLogManager(main);
        final String command = preprocessEvent.getMessage();
        final String sender = preprocessEvent.getPlayer().getName();
        final UUID senderUUID = preprocessEvent.getPlayer().getUniqueId();

        activityLogManager.logCommand(sender, senderUUID.toString(), command);

    }
}
