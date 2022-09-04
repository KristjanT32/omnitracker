package krisapps.omnitracker.events;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.managers.ActivityLogManager;
import krisapps.omnitracker.enums.OperationType;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.HashMap;

public class OnGamemodeChangeHandler implements Listener {

    OmniTracker main;
    public OnGamemodeChangeHandler(OmniTracker main){
        this.main = main;
    }

    @EventHandler
    void OnGamemodeChange(PlayerGameModeChangeEvent modeChangeEvent){
        ActivityLogManager activityLogManager = new ActivityLogManager(main);
        final Player player = modeChangeEvent.getPlayer();
        final GameMode previousGameMode = modeChangeEvent.getPlayer().getGameMode();
        final GameMode newGameMode = modeChangeEvent.getNewGameMode();

        HashMap<String, String> args = new HashMap<>();
        args.put("arg1", previousGameMode.name());
        args.put("arg2", newGameMode.name());

        activityLogManager.logOperation(player.getName(), player.getUniqueId().toString(), OperationType.GAMEMODE_CHANGE, args);
    }
}
