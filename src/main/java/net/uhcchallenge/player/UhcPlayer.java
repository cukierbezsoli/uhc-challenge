package net.uhcchallenge.player;

public interface UhcPlayer {
    int uhc_challenge$getProtectionTicks();
    void uhc_challenge$setProtectionTicks(int ticks);
    boolean uhc_challenge$hasProtectionExpired();
    void uhc_challenge$setProtectionExpired(boolean expired);
    boolean uhc_challenge$tookBaseDamage();
    void uhc_challenge$setTookBaseDamage(boolean tookBaseDamage);
    
    boolean uhc_challenge$hasSleeplessNightsTriggered();
    void uhc_challenge$setSleeplessNightsTriggered(boolean triggered);
    
    boolean uhc_challenge$raidDamageTaken();
    void uhc_challenge$setRaidDamageTaken(boolean damageTaken);
    
    float uhc_challenge$endDamageTaken();
    void uhc_challenge$setEndDamageTaken(float damage);
    
    boolean uhc_challenge$wasLowHealth();
    void uhc_challenge$setWasLowHealth(boolean lowHealth);
    
    boolean uhc_challenge$hasBeenWelcomed();
    void uhc_challenge$setBeenWelcomed(boolean welcomed);
}
