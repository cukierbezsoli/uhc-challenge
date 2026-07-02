package net.uhcchallenge.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public double fallDistance;
    @Shadow public abstract Level level();

    @Unique
    private double uhc_previousFallDistance = 0.0D;

    @Inject(method = "updateFluidInteraction", at = @At("HEAD"))
    private void uhc_onWaterUpdate(CallbackInfoReturnable<Boolean> cir) {
        if (!this.level().isClientSide() && (Object) this instanceof ServerPlayer) {
            this.uhc_previousFallDistance = this.fallDistance;
        }
    }
    
    @Inject(method = "updateFluidInteraction", at = @At("TAIL"))
    private void uhc_onWaterUpdateTail(CallbackInfoReturnable<Boolean> cir) {
        if (!this.level().isClientSide() && (Object) this instanceof ServerPlayer player) {
            if (cir.getReturnValueZ() && this.uhc_previousFallDistance >= 100.0D) {
                net.uhcchallenge.advancement.UHCChallengeCriteria.WATER_MLG.trigger(player);
            }
        }
    }
}
