package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.world.TeleportTarget

/**
 * Callback for before a player is teleported via a Nether or End portal.
 * @see com.viral32111.events.mixin.server.EntityMixin.tickPortalTeleportation
 * @see com.viral32111.events.listener.server.playerEnterPortalCallbackListener
 * @since 0.3.4
 */
fun interface PlayerEnterPortalCallback {
	companion object {
		val EVENT: Event<PlayerEnterPortalCallback> =
			EventFactory.createArrayBacked(PlayerEnterPortalCallback::class.java) { listeners ->
				PlayerEnterPortalCallback { player, teleportTarget ->
					for (listener in listeners) {
						val result = listener.interact(player, teleportTarget)
						if (result != ActionResult.PASS) return@PlayerEnterPortalCallback result
					}

					return@PlayerEnterPortalCallback ActionResult.PASS
				}
			}
	}

	/**
	 * Runs before a player is teleported to another dimension via a Nether or End portal.
	 * @param player The player that is about to be teleported via a portal.
	 * @param teleportTarget The position & dimension the player will be teleported to.
	 * @return Pass to allow the player to be teleported, fail to prevent the player from being teleported.
	 * @since 0.3.4
	 */
	fun interact(player: ServerPlayerEntity, teleportTarget: TeleportTarget): ActionResult
}
