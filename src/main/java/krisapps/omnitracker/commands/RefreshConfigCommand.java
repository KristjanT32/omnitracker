package krisapps.omnitracker.commands;

import krisapps.omnitracker.OmniTracker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RefreshConfigCommand implements CommandExecutor {

    OmniTracker main;
    public RefreshConfigCommand(OmniTracker main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        sender.sendMessage("&e[&bOmniTracker&e]&d: &aRefreshing config...");
        try {
            main.getConfig().load(main.configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        sender.sendMessage("&e[&bOmniTracker&e]&d: &Done!");
        return true;
    }
}
