package com.viral32111.events

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.InteractionResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.roundToInt

@Suppress("UNUSED")
class Main : ModInitializer {

	// A logger writes text to the console & log file.
	// It is best practice to use your mod id as the logger's name, so it is clear which mod wrote messages.
	companion object {
		private const val MOD_ID = "events"
		val LOGGER: Logger = LoggerFactory.getLogger("com.viral32111.$MOD_ID")

		/**
		 * Gets the current version of this mod.
		 * @since 0.3.5
		 */
		fun getModVersion(): String =
			FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow {
				throw IllegalStateException("Mod container not found")
			}.metadata.version.friendlyString
	}

	// Runs when the mod has initialized...
	override fun onInitialize() {
		LOGGER.info("Events v${getModVersion()} initialized.")

		// Register all shared callback listeners
		//registerPlayerCallbackListeners()
	}

	/**
	 * Registers example listeners for player callbacks provided by the Fabric API.
	 * @see net.fabricmc.fabric.api.event.player
	 * @since 0.3.1
	 */
	private fun registerPlayerCallbackListeners() {

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/player/AttackBlockCallback.html, https://fabricmc.net/wiki/tutorial:callbacks
		AttackBlockCallback.EVENT.register { player, world, _, position, _ ->
			val blockState = world.getBlockState(position)
			val id = BuiltInRegistries.BLOCK.getKey(blockState.block)
			LOGGER.info("Player '${player.name.string}' (${player.stringUUID}) attacked block '${blockState.block.name.string}' (${id}) [${position.x}, ${position.y}, ${position.z}] in world '${world.server?.worldData?.levelName}' (${world.dimension()}).")
			return@register InteractionResult.PASS
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/player/UseBlockCallback.html
		UseBlockCallback.EVENT.register { player, world, _, result ->
			val blockState = world.getBlockState(result.blockPos)
			val id = BuiltInRegistries.BLOCK.getKey(blockState.block)
			LOGGER.info("Player '${player.name.string}' (${player.stringUUID}) used block '${blockState.block.name.string}' (${id}) [${result.blockPos.x}, ${result.blockPos.y}, ${result.blockPos.z}] in world '${world.server?.worldData?.levelName}' (${world.dimension()}).")
			return@register InteractionResult.PASS
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/player/AttackEntityCallback.html
		AttackEntityCallback.EVENT.register { player, world, _, entity, _ ->
			LOGGER.info("Player '${player.name.string}' (${player.stringUUID}) attacked entity '${entity.name.string}' (${entity.type}) at [${entity.x.roundToInt()}, ${entity.y.roundToInt()}, ${entity.z.roundToInt()}] in world '${world.server?.worldData?.levelName}' (${world.dimension()}).")
			return@register InteractionResult.PASS
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/player/UseEntityCallback.html
		UseEntityCallback.EVENT.register { player, world, _, entity, _ ->
			LOGGER.info("Player '${player.name.string}' (${player.stringUUID}) used entity '${entity.name.string}' (${entity.type}) at [${entity.x.roundToInt()}, ${entity.y.roundToInt()}, ${entity.z.roundToInt()}] in world '${world.server?.worldData?.levelName}' (${world.dimension()}).")
			return@register InteractionResult.PASS
		}

	}

}
