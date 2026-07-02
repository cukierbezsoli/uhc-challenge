package net.uhcchallenge.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public abstract class ItemsMixin {

    @Inject(method = "registerItem(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", at = @At("HEAD"))
    private static void uhc_onRegisterItem(ResourceKey<Item> key, Item.Properties properties, CallbackInfoReturnable<Item> cir) {
        if (key.identifier().getPath().equals("golden_apple")) {
            net.minecraft.world.item.component.Consumable.Builder builder = net.minecraft.world.item.component.Consumable.builder();
            builder.consumeSeconds(1.2f);
            builder.animation(net.minecraft.world.item.ItemUseAnimation.EAT);
            builder.sound(net.minecraft.sounds.SoundEvents.GENERIC_EAT);
            builder.hasConsumeParticles(true);
            builder.onConsume(new net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect(
                    new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.REGENERATION, 400, 0), 1.0F));
            builder.onConsume(new net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect(
                    new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.ABSORPTION, 6000, 0), 1.0F));
            properties.component(net.minecraft.core.component.DataComponents.CONSUMABLE, builder.build());
        } else if (key.identifier().getPath().equals("enchanted_golden_apple")) {
            net.minecraft.world.item.component.Consumable.Builder builder = net.minecraft.world.item.component.Consumable.builder();
            builder.consumeSeconds(1.2f);
            builder.animation(net.minecraft.world.item.ItemUseAnimation.EAT);
            builder.sound(net.minecraft.sounds.SoundEvents.GENERIC_EAT);
            builder.hasConsumeParticles(true);
            builder.onConsume(new net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect(
                    new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.REGENERATION, 400, 1), 1.0F));
            builder.onConsume(new net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect(
                    new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.ABSORPTION, 6000, 3), 1.0F));
            builder.onConsume(new net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect(
                    new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.RESISTANCE, 6000, 0), 1.0F));
            builder.onConsume(new net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect(
                    new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0F));
            properties.component(net.minecraft.core.component.DataComponents.CONSUMABLE, builder.build());
        }
    }
}
