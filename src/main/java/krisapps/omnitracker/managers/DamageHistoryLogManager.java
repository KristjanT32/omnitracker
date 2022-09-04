package krisapps.omnitracker.managers;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.exceptions.DamageTargetNotMobException;
import krisapps.omnitracker.exceptions.DamageTargetNotPlayerException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DamageHistoryLogManager {

    OmniTracker main;
    public DamageHistoryLogManager(OmniTracker main){
        this.main = main;
    }

    public void logDamageFromPlayer(Entity damager, Entity target, EntityDamageEvent.DamageCause cause, double damageAmount){

        if (damager instanceof Player){
            Player damageInducer = ((Player) damager).getPlayer();
            Player targetPlayer = ((Player) target).getPlayer();

            File playerLogFile = new File(main.getDataFolder(), "/player-log-files/" + targetPlayer.getName());
            if (!playerLogFile.exists()){
                try {
                    playerLogFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            FileWriter fw;
            try {
                fw = new FileWriter(playerLogFile, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fw);

            String message = String.format("Player received %s damage (of type %s) from %s", String.valueOf(damageAmount), cause.name(), damageInducer.getName());

            pw.println(String.format("(%s)-[PlayerLog/%s]: " + message, format.format(now), target.getName()));
            pw.flush();
            pw.close();


        }else{
            try {
                throw new DamageTargetNotPlayerException("Specified damage receiver is not a player.");
            } catch (DamageTargetNotPlayerException e) {
                e.printStackTrace();
            }
        }

    }

    public void logDamageFromLivingEntity(Entity damager, Entity target, EntityDamageEvent.DamageCause cause, double damageAmount){

        if (damager instanceof Mob){
            Player targetPlayer = ((Player) target).getPlayer();

            File playerLogFile = new File(main.getDataFolder(), "/player-log-files/" + targetPlayer.getName());
            if (!playerLogFile.exists()){
                try {
                    playerLogFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            FileWriter fw;
            try {
                fw = new FileWriter(playerLogFile, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fw);

            String message = String.format("Player received %s damage (of type %s) from entity %s", String.valueOf(damageAmount), cause.name(), damager.getName());

            pw.println(String.format("(%s)-[PlayerLog/%s]: " + message, format.format(now), target.getName()));
            pw.flush();
            pw.close();

        }else{
            try {
                throw new DamageTargetNotMobException("Specified damage target is not a mob.");
            } catch (DamageTargetNotMobException e) {
                e.printStackTrace();
            }
        }

    }




}
