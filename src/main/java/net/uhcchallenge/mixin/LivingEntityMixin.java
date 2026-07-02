package net.uhcchallenge.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.uhcchallenge.player.UhcPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "setHealth", at = @At("HEAD"))
    private void uhc_onHealthChange(float health, CallbackInfo ci) {
        if ((Object) this instanceof ServerPlayer player) {
            UhcPlayer uhcPlayer = (UhcPlayer) player;
            float currentHealth = player.getHealth();
            if (health < currentHealth) {
                // Obrażenia zadane w okresie ochronnym
                if (!uhcPlayer.uhc_challenge$hasProtectionExpired()) {
                    uhcPlayer.uhc_challenge$setTookBaseDamage(true);
                }
                
                // Otarłeś się o śmierć - łapanie spadku zdrowia
                if (health <= 10.0F) {
                    uhcPlayer.uhc_challenge$setWasLowHealth(true);
                }
                
                // Raid bez obrażeń
                if (((net.minecraft.server.level.ServerLevel)player.level()).getRaidAt(player.blockPosition()) != null) {
                    uhcPlayer.uhc_challenge$setRaidDamageTaken(true);
                }
                
                // Pogromca smoków
                if (player.level().dimension() == net.minecraft.world.level.Level.END) {
                    net.minecraft.world.level.dimension.end.EnderDragonFight fight = ((net.minecraft.server.level.ServerLevel)player.level()).getDragonFight();
                    if (fight != null && !fight.hasPreviouslyKilledDragon()) {
                        uhcPlayer.uhc_challenge$setEndDamageTaken(uhcPlayer.uhc_challenge$endDamageTaken() + (currentHealth - health));
                    }
                }
            } else if (health > currentHealth) {
                // Leczenie
                if (uhcPlayer.uhc_challenge$wasLowHealth() && health >= 40.0F) {
                    net.uhcchallenge.advancement.UHCChallengeCriteria.NEAR_DEATH.trigger(player);
                    uhcPlayer.uhc_challenge$setWasLowHealth(false);
                }
            }
        }
    }
}
