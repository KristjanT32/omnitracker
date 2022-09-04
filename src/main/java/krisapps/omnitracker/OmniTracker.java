package krisapps.omnitracker;

import krisapps.omnitracker.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class OmniTracker extends JavaPlugin {
    FileConfiguration config;

    public File configFile = new File(getDataFolder(), "config.yml");
    public File messageLogFile = new File(getDataFolder(), "/logs/messages.log");
    public File commandsLogFile = new File(getDataFolder(), "/logs/commands.log");
    public File operationsLogFile = new File(getDataFolder(), "/logs/operations.log");
    public File generalLogFile = new File(getDataFolder(), "/logs/general.log");

    @Override
    public void onEnable(){

        registerFiles();
        registerComponents();

    }

    @Override
    public void onDisable(){

    }

    // Check files, generate new if needed
    void registerFiles(){

        getLogger().info("Initializing logger files...");

        if (!getDataFolder().exists()){
            saveResource("config.yml", true);
            configFile.getParentFile().mkdirs();
            try {
                messageLogFile.createNewFile();
                commandsLogFile.createNewFile();
                operationsLogFile.createNewFile();
            }catch (IOException e){
                getLogger().severe("Error initializing logger files: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }else{

            if (!generalLogFile.getParentFile().exists() || !generalLogFile.exists()){
                try {
                    generalLogFile.createNewFile();
                } catch (IOException e) {
                    getLogger().warning("Could not initialize general log file: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (!configFile.getParentFile().exists() || !configFile.exists()){
                try {
                    configFile.createNewFile();
                } catch (IOException e) {
                    getLogger().warning("Could not initialize configuration file: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (!messageLogFile.getParentFile().exists() || !messageLogFile.exists()){
                try {
                    messageLogFile.createNewFile();
                } catch (IOException e) {
                    getLogger().warning("Could not initialize message log file: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (!commandsLogFile.getParentFile().exists() || !commandsLogFile.exists()){
                try {
                    commandsLogFile.createNewFile();
                } catch (IOException e) {
                    getLogger().warning("Could not initialize krisapps.activitylogger.commands log file: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (!operationsLogFile.getParentFile().exists() || !operationsLogFile.exists()){
                try {
                    operationsLogFile.createNewFile();
                } catch (IOException e) {
                    getLogger().warning("Could not initialize operations log file: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            loadFiles();
        }


    }

    void loadFiles() {
        config = new YamlConfiguration();

        try {
            config.load(configFile);
        }catch (InvalidConfigurationException | IOException e){
            getLogger().warning("Could not load plugin configuration file: " + e.getMessage());
            e.printStackTrace();
        }

        getLogger().info("Initialization complete!");

    }

    // Register commands, event handlers, tab completion etc.
    void registerComponents(){

        getServer().getPluginManager().registerEvents(new OnMessageSentHandler(this), this);
        getServer().getPluginManager().registerEvents(new OnCommandHandler(this), this);
        getServer().getPluginManager().registerEvents(new OnGamemodeChangeHandler(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveHandler(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerTeleportHandler(this), this);

        for (Player p: Bukkit.getOnlinePlayers()){
            if (p.isOp()){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bActivityLogger&e]&d: &aStarted logging activities [/]"));
            }
        }
    }




}
