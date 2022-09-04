package krisapps.omnitracker.events;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.managers.ActivityLogManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class OnMessageSentHandler implements Listener {

    OmniTracker main;
    public OnMessageSentHandler(OmniTracker main){
        this.main = main;
    }

    @EventHandler
    void OnChatMessageSent(AsyncPlayerChatEvent chatEvent){
        ActivityLogManager activityLogManager = new ActivityLogManager(main);

        final String message = chatEvent.getMessage();
        final String sender = chatEvent.getPlayer().getName();
        final UUID senderUUID = chatEvent.getPlayer().getUniqueId();

        activityLogManager.logMessage(sender, senderUUID.toString(), message);
    }
}
