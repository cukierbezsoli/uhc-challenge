package net.uhcchallenge.advancement;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.uhcchallenge.UHCChallenge;

public class UHCChallengeCriteria {
    public static final UhcSimpleCriterion SURVIVED_PROTECTION = register("survived_protection");
    public static final UhcSimpleCriterion SLEEPLESS_NIGHTS = register("sleepless_nights");
    public static final UhcSimpleCriterion WATER_MLG = register("water_mlg");
    public static final UhcSimpleCriterion NEAR_DEATH = register("near_death");
    public static final UhcSimpleCriterion FLAWLESS_RAID = register("flawless_raid");
    public static final UhcSimpleCriterion FLAWLESS_DRAGON = register("flawless_dragon");

    private static UhcSimpleCriterion register(String name) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, UHCChallenge.id(name), new UhcSimpleCriterion());
    }

    public static void initialize() {
        // Forces static initialization
    }
}
