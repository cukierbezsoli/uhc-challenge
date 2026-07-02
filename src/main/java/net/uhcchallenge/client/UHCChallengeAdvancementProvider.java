package net.uhcchallenge.client;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.world.item.Items;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.uhcchallenge.UHCChallenge;
import net.uhcchallenge.advancement.UHCChallengeCriteria;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UHCChallengeAdvancementProvider extends FabricAdvancementProvider {

    public UHCChallengeAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        // Root advancement
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                        Items.GOLD_BLOCK,
                        Component.literal("UHC Challenge"),
                        Component.literal("Survive the most balanced challenge!"),
                        Identifier.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                        AdvancementType.TASK,
                        false, // showToast
                        false, // announceToChat
                        false  // hidden
                )
                .addCriterion("survived_protection", UHCChallengeCriteria.SURVIVED_PROTECTION.createCriterion(net.uhcchallenge.advancement.UhcSimpleCriterion.Conditions.create()))
                .save(consumer, UHCChallenge.id("root").toString());

        AdvancementHolder barieraPekla = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        Items.SHIELD,
                        Component.literal("The Barrier is Broken"),
                        Component.literal("Survive the first 10 minutes without losing any red hearts."),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("survived_protection", UHCChallengeCriteria.SURVIVED_PROTECTION.createCriterion(net.uhcchallenge.advancement.UhcSimpleCriterion.Conditions.create()))
                .save(consumer, UHCChallenge.id("bariera_pekla").toString());

        AdvancementHolder alchemiaUbogich = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        Items.GOLDEN_APPLE,
                        Component.literal("Alchemy for the Poor"),
                        Component.literal("Craft or obtain a Golden Apple."),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_apple", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLDEN_APPLE))
                .save(consumer, UHCChallenge.id("alchemia_ubogich").toString());

        AdvancementHolder zakazanaTechnologia = Advancement.Builder.advancement()
                .parent(alchemiaUbogich)
                .display(
                        Items.ENCHANTED_GOLDEN_APPLE,
                        Component.literal("Forbidden Technology"),
                        Component.literal("Obtain the legendary Enchanted Golden Apple."),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("has_kox", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ENCHANTED_GOLDEN_APPLE))
                .save(consumer, UHCChallenge.id("zakazana_technologia").toString());

        AdvancementHolder spokojnySen = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        Items.CAMPFIRE,
                        Component.literal("Peaceful Sleep"),
                        Component.literal("Survive 3 nights without attempting to sleep."),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("sleepless", UHCChallengeCriteria.SLEEPLESS_NIGHTS.createCriterion(net.uhcchallenge.advancement.UhcSimpleCriterion.Conditions.create()))
                .save(consumer, UHCChallenge.id("spokojny_sen").toString());

        AdvancementHolder waterMlg = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        Items.WATER_BUCKET,
                        Component.literal("Gravity is Just a Suggestion"),
                        Component.literal("Perform an MLG from at least 100 blocks."),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("water_mlg", UHCChallengeCriteria.WATER_MLG.createCriterion(net.uhcchallenge.advancement.UhcSimpleCriterion.Conditions.create()))
                .save(consumer, UHCChallenge.id("water_mlg").toString());

        AdvancementHolder otarlesSie = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        Items.TOTEM_OF_UNDYING,
                        Component.literal("Brush with Death"),
                        Component.literal("Drop to 5 hearts or fewer, then heal back to full."),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("near_death", UHCChallengeCriteria.NEAR_DEATH.createCriterion(net.uhcchallenge.advancement.UhcSimpleCriterion.Conditions.create()))
                .save(consumer, UHCChallenge.id("otarles_sie_o_smierc").toString());

        AdvancementHolder obroncaBezSkazy = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        Items.CROSSBOW,
                        Component.literal("Flawless Defender"),
                        Component.literal("Win a raid without losing a single health point."),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("flawless_raid", UHCChallengeCriteria.FLAWLESS_RAID.createCriterion(net.uhcchallenge.advancement.UhcSimpleCriterion.Conditions.create()))
                .save(consumer, UHCChallenge.id("obronca_bez_skazy").toString());

        AdvancementHolder pogromcaSmokow = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        Items.DRAGON_HEAD,
                        Component.literal("Dragon Slayer"),
                        Component.literal("Defeat the Ender Dragon losing a maximum of 5 hearts in the End."),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("flawless_dragon", UHCChallengeCriteria.FLAWLESS_DRAGON.createCriterion(net.uhcchallenge.advancement.UhcSimpleCriterion.Conditions.create()))
                .save(consumer, UHCChallenge.id("pogromca_smokow").toString());
    }
}
