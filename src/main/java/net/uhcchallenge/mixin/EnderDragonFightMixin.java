package net.uhcchallenge.mixin;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.dimension.end.EnderDragonFight;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.uhcchallenge.player.UhcPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin {

    @Shadow @Final private ServerLevel level;

    @Inject(method = "setDragonKilled", at = @At("HEAD"))
    private void uhc_onDragonKilled(EnderDragon dragon, CallbackInfo ci) {
        for (ServerPlayer player : this.level.players()) {
            UhcPlayer uhcPlayer = (UhcPlayer) player;
            if (uhcPlayer.uhc_challenge$endDamageTaken() <= 10.0F) {
                net.uhcchallenge.advancement.UHCChallengeCriteria.FLAWLESS_DRAGON.trigger(player);
            }
        }
    }
}
