package net.uhcchallenge;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UHCChallenge implements ModInitializer {
	public static final String MOD_ID = "uhc-challenge";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing UHC Challenge!");
		net.uhcchallenge.advancement.UHCChallengeCriteria.initialize();
		
		net.fabricmc.fabric.api.entity.event.v1.ServerEntityLevelChangeEvents.AFTER_PLAYER_CHANGE_LEVEL.register((player, origin, destination) -> {
			if (destination.dimension() == net.minecraft.world.level.Level.END) {
				((net.uhcchallenge.player.UhcPlayer) player).uhc_challenge$setEndDamageTaken(0.0f);
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(net.minecraft.commands.Commands.literal("uhc")
				.executes(context -> {
					if (context.getSource().getEntity() instanceof ServerPlayer player) {
						sendWelcomeMessage(player);
					}
					return 1;
				}));
		});
	}

	public static void sendWelcomeMessage(ServerPlayer player) {
		player.sendSystemMessage(Component.literal("").append(Component.literal("=== UHC Challenge ===").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD)), false);
		player.sendSystemMessage(Component.literal("- You have 20 hearts of base health.").withStyle(ChatFormatting.YELLOW), false);
		player.sendSystemMessage(Component.literal("- For the first 10 minutes you get an additional 8 yellow hearts (Absorption IV).").withStyle(ChatFormatting.YELLOW), false);
		player.sendSystemMessage(Component.literal("- Golden Apples are more powerful! Test out the Enchanted ones as well.").withStyle(ChatFormatting.YELLOW), false);
		player.sendSystemMessage(Component.literal("- Special UHC challenges and advancements await you.").withStyle(ChatFormatting.YELLOW), false);
		player.sendSystemMessage(Component.literal("Made with Google Antigravity by @cukierbezsoli").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC), false);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
