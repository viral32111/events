package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player

/**
 * Callback for after a player leaves the server.
 * @see com.viral32111.events.mixin.server.PlayerManagerMixin.remove
 * @see com.viral32111.events.listener.server.playerLeaveCallbackListener
 * @since 0.2.0
 */
fun interface PlayerLeaveCallback {
	companion object {
		val EVENT: Event<PlayerLeaveCallback> =
			EventFactory.createArrayBacked(PlayerLeaveCallback::class.java) { listeners ->
				PlayerLeaveCallback { player ->
					for (listener in listeners) {
						val result = listener.interact(player)
						if (result != InteractionResult.PASS) return@PlayerLeaveCallback result
					}

					return@PlayerLeaveCallback InteractionResult.PASS
				}
			}
	}

	/**
	 * Runs when a player has left the server.
	 * @param player The player that left the server.
	 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
	 * @see PlayerLeaveCallback
	 * @since 0.2.0
	 */
	fun interact(player: Player): InteractionResult
}
