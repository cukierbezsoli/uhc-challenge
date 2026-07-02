package net.uhcchallenge.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.uhcchallenge.player.UhcPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin implements UhcPlayer {

    @Unique
    private int protectionTicks = 0;
    @Unique
    private boolean protectionExpired = false;
    @Unique
    private boolean tookBaseDamage = false;
    @Unique
    private boolean sleeplessNightsTriggered = false;
    @Unique
    private boolean raidDamageTaken = false;
    @Unique
    private float endDamageTaken = 0.0f;
    @Unique
    private boolean wasLowHealth = false;
    @Unique
    private boolean welcomed = false;
    
    @Unique
    private static final int MAX_PROTECTION_TICKS = 12000;

    @Override
    public int uhc_challenge$getProtectionTicks() {
        return protectionTicks;
    }

    @Override
    public void uhc_challenge$setProtectionTicks(int ticks) {
        this.protectionTicks = ticks;
    }

    @Override
    public boolean uhc_challenge$hasProtectionExpired() {
        return protectionExpired;
    }

    @Override
    public void uhc_challenge$setProtectionExpired(boolean expired) {
        this.protectionExpired = expired;
    }

    @Override
    public boolean uhc_challenge$tookBaseDamage() {
        return tookBaseDamage;
    }

    @Override
    public void uhc_challenge$setTookBaseDamage(boolean tookBaseDamage) {
        this.tookBaseDamage = tookBaseDamage;
    }

    @Override
    public boolean uhc_challenge$hasSleeplessNightsTriggered() {
        return sleeplessNightsTriggered;
    }

    @Override
    public void uhc_challenge$setSleeplessNightsTriggered(boolean triggered) {
        this.sleeplessNightsTriggered = triggered;
    }

    @Override
    public boolean uhc_challenge$raidDamageTaken() {
        return raidDamageTaken;
    }

    @Override
    public void uhc_challenge$setRaidDamageTaken(boolean damageTaken) {
        this.raidDamageTaken = damageTaken;
    }

    @Override
    public float uhc_challenge$endDamageTaken() {
        return endDamageTaken;
    }

    @Override
    public void uhc_challenge$setEndDamageTaken(float damage) {
        this.endDamageTaken = damage;
    }

    @Override
    public boolean uhc_challenge$wasLowHealth() {
        return wasLowHealth;
    }

    @Override
    public void uhc_challenge$setWasLowHealth(boolean lowHealth) {
        this.wasLowHealth = lowHealth;
    }

    @Override
    public boolean uhc_challenge$hasBeenWelcomed() {
        return welcomed;
    }

    @Override
    public void uhc_challenge$setBeenWelcomed(boolean welcomed) {
        this.welcomed = welcomed;
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void uhc_readNbt(net.minecraft.world.level.storage.ValueInput nbt, CallbackInfo ci) {
        if (nbt.contains("UhcProtectionTicks")) {
            this.protectionTicks = nbt.getIntOr("UhcProtectionTicks", 0);
        }
        if (nbt.contains("UhcProtectionExpired")) {
            this.protectionExpired = nbt.getBooleanOr("UhcProtectionExpired", false);
        }
        if (nbt.contains("UhcTookBaseDamage")) {
            this.tookBaseDamage = nbt.getBooleanOr("UhcTookBaseDamage", false);
        }
        if (nbt.contains("UhcSleeplessNightsTriggered")) {
            this.sleeplessNightsTriggered = nbt.getBooleanOr("UhcSleeplessNightsTriggered", false);
        }
        if (nbt.contains("UhcRaidDamageTaken")) {
            this.raidDamageTaken = nbt.getBooleanOr("UhcRaidDamageTaken", false);
        }
        if (nbt.contains("UhcEndDamageTaken")) {
            this.endDamageTaken = nbt.getFloatOr("UhcEndDamageTaken", 0.0f);
        }
        if (nbt.contains("UhcWasLowHealth")) {
            this.wasLowHealth = nbt.getBooleanOr("UhcWasLowHealth", false);
        }
        if (nbt.contains("UhcWelcomed")) {
            this.welcomed = nbt.getBooleanOr("UhcWelcomed", false);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void uhc_writeNbt(net.minecraft.world.level.storage.ValueOutput nbt, CallbackInfo ci) {
        nbt.putInt("UhcProtectionTicks", this.protectionTicks);
        nbt.putBoolean("UhcProtectionExpired", this.protectionExpired);
        nbt.putBoolean("UhcTookBaseDamage", this.tookBaseDamage);
        nbt.putBoolean("UhcSleeplessNightsTriggered", this.sleeplessNightsTriggered);
        nbt.putBoolean("UhcRaidDamageTaken", this.raidDamageTaken);
        nbt.putFloat("UhcEndDamageTaken", this.endDamageTaken);
        nbt.putBoolean("UhcWasLowHealth", this.wasLowHealth);
        nbt.putBoolean("UhcWelcomed", this.welcomed);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void uhc_tickProtection(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        
        if (player.isSpectator() || player.isCreative()) {
            return;
        }

        if (!((net.minecraft.server.level.ServerLevel) player.level()).getServer().isHardcore()) {
            if (player.getAttribute(Attributes.MAX_HEALTH).getBaseValue() != 20.0) {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0);
            }
            return;
        }

        if (player.getAttribute(Attributes.MAX_HEALTH).getBaseValue() != 40.0) {
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0);
            player.setHealth(40.0F);
        }

        if (((net.minecraft.server.level.ServerLevel)player.level()).getGameRules().get(net.minecraft.world.level.gamerules.GameRules.NATURAL_HEALTH_REGENERATION)) {
            ((net.minecraft.server.level.ServerLevel)player.level()).getGameRules().set(net.minecraft.world.level.gamerules.GameRules.NATURAL_HEALTH_REGENERATION, false, ((net.minecraft.server.level.ServerLevel)player.level()).getServer());
        }

        if (!this.welcomed) {
            this.welcomed = true;
            player.connection.send(new net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket(Component.literal("UHC challenge").withStyle(ChatFormatting.RED, ChatFormatting.BOLD)));
            player.connection.send(new net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket(Component.literal("ver. 1.0").withStyle(ChatFormatting.GRAY)));
            player.connection.send(new net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket(10, 70, 20));
            net.uhcchallenge.UHCChallenge.sendWelcomeMessage(player);
            
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, MAX_PROTECTION_TICKS, 3, false, false, true));
        }

        if (!protectionExpired) {
            protectionTicks++;
            
            if (protectionTicks >= MAX_PROTECTION_TICKS) {
                protectionExpired = true;
                player.sendSystemMessage(Component.literal("⚔ The grace period has ended! Good luck!").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), true);
                player.removeEffect(MobEffects.ABSORPTION);
                
                if (!this.tookBaseDamage) {
                    net.uhcchallenge.advancement.UHCChallengeCriteria.SURVIVED_PROTECTION.trigger(player);
                }
            }
        }
        
        if (!this.sleeplessNightsTriggered) {
            int timeSinceRest = player.getStats().getValue(net.minecraft.stats.Stats.CUSTOM.get(net.minecraft.stats.Stats.TIME_SINCE_REST));
            if (timeSinceRest >= 72000) {
                net.uhcchallenge.advancement.UHCChallengeCriteria.SLEEPLESS_NIGHTS.trigger(player);
                this.sleeplessNightsTriggered = true;
            }
        }
        
        if (((net.minecraft.server.level.ServerLevel)player.level()).getRaidAt(player.blockPosition()) == null) {
            this.raidDamageTaken = false;
        }
    }
}
