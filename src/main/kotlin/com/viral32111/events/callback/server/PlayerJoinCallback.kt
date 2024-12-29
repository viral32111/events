package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.ClientConnection
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

/**
 * Callback for after a player joins the server.
 * @see com.viral32111.events.mixin.server.PlayerManagerMixin.onPlayerConnect
 * @see com.viral32111.events.listener.server.playerJoinCallbackListener
 * @since 0.2.0
 */
fun interface PlayerJoinCallback {
	companion object {
		val EVENT: Event<PlayerJoinCallback> =
			EventFactory.createArrayBacked(PlayerJoinCallback::class.java) { listeners ->
				PlayerJoinCallback { connection, player ->
					for (listener in listeners) {
						val result = listener.interact(connection, player)
						if (result != ActionResult.PASS) return@PlayerJoinCallback result
					}

					return@PlayerJoinCallback ActionResult.PASS
				}
			}
	}

	/**
	 * Runs when a player has joined the server.
	 * @param connection The IP address & port number of the player that joined the server.
	 * @param player The player that joined the server.
	 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
	 * @see PlayerJoinCallback
	 * @since 0.2.0
	 */
	fun interact(connection: ClientConnection, player: ServerPlayerEntity): ActionResult
}
