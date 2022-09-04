package krisapps.omnitracker.events;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.managers.DamageHistoryLogManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnPlayerDamageReceivedHandler implements Listener {

    OmniTracker main;
    public OnPlayerDamageReceivedHandler(OmniTracker main){
        this.main = main;
    }

    @EventHandler
    void OnPlayerDamageReceived(EntityDamageByEntityEvent damageEvent){

        DamageHistoryLogManager historyLogManager = new DamageHistoryLogManager(main);

        final Entity damageInducer = damageEvent.getDamager();
        final Entity damagedEntity = damageEvent.getEntity();
        final double damageAmount = damageEvent.getDamage();
        final EntityDamageEvent.DamageCause damageCause = damageEvent.getCause();

        if (main.getConfig().getBoolean("general.recordPlayerDamage")) {

            if (damageInducer instanceof Player) {
                historyLogManager.logDamageFromPlayer(damageInducer, damagedEntity, damageCause, damageAmount);
            } else {
                historyLogManager.logDamageFromLivingEntity(damageInducer, damagedEntity, damageCause, damageAmount);
            }

        }

    }

}
