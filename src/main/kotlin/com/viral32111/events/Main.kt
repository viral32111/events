package com.viral32111.events

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.AttackBlockCallback
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.ActionResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import kotlin.math.roundToInt

@Suppress( "UNUSED" )
class Main: ModInitializer {

	// A logger writes text to the console & log file.
	// It is best practice to use your mod id as the logger's name, so it is clear which mod wrote messages.
	companion object {
		private const val MOD_ID = "events"
		val LOGGER: Logger = LoggerFactory.getLogger( "com.viral32111.$MOD_ID" )

		/**
		 * Gets the current version of this mod.
		 * @since 0.3.5
		 */
		fun getModVersion(): String =
			FabricLoader.getInstance().getModContainer( MOD_ID ).orElseThrow {
				throw IllegalStateException( "Mod container not found" )
			}.metadata.version.friendlyString
	}

	// Runs when the mod has initialized...
	override fun onInitialize() {
		LOGGER.info( "Events v${ getModVersion() } initialized." )

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
			val blockState = world.getBlockState( position )
			LOGGER.info( "Player '${ player.name.string }' (${ player.uuidAsString }) attacked block '${ blockState.block.name.string }' (${ blockState.registryEntry.key.get().value }) [${ position.x }, ${ position.y }, ${ position.z }] in world '${ world.server?.saveProperties?.levelName  }' (${ world.registryKey.value })." )
			return@register ActionResult.PASS
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/player/UseBlockCallback.html
		UseBlockCallback.EVENT.register { player, world, _, result ->
			val blockState = world.getBlockState( result.blockPos )
			LOGGER.info( "Player '${ player.name.string }' (${ player.uuidAsString }) used block '${ blockState.block.name.string }' (${ blockState.registryEntry.key.get().value }) [${ result.blockPos.x }, ${ result.blockPos.y }, ${ result.blockPos.z }] in world '${ world.server?.saveProperties?.levelName }' (${ world.registryKey.value })." )
			return@register ActionResult.PASS
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/player/AttackEntityCallback.html
		AttackEntityCallback.EVENT.register { player, world, _, entity, _ ->
			LOGGER.info( "Player '${ player.name.string }' (${ player.uuidAsString }) attacked entity '${ entity.name.string }' (${ entity.type }) at [${ entity.pos.x.roundToInt() }, ${ entity.pos.y.roundToInt() }, ${ entity.pos.z.roundToInt() }] in world '${ world.server?.saveProperties?.levelName  }' (${ world.registryKey.value })." )
			return@register ActionResult.PASS
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/player/UseEntityCallback.html
		UseEntityCallback.EVENT.register { player, world, _, entity, _ ->
			LOGGER.info( "Player '${ player.name.string }' (${ player.uuidAsString }) used entity '${ entity.name.string }' (${ entity.type }) at [${ entity.pos.x.roundToInt() }, ${ entity.pos.y.roundToInt() }, ${ entity.pos.z.roundToInt() }] in world '${ world.server?.saveProperties?.levelName  }' (${ world.registryKey.value })." )
			return@register ActionResult.PASS
		}

	}

}
