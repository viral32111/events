package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

/**
 * Callback for after a player sends a chat message.
 * @see com.viral32111.events.mixin.server.ServerPlayNetworkHandlerMixin.onChatMessage
 * @see com.viral32111.events.listener.server.playerChatMessageCallbackListener
 * @since 0.2.0
 */
fun interface PlayerChatMessageCallback {
	companion object {
		val EVENT: Event<PlayerChatMessageCallback> = EventFactory.createArrayBacked( PlayerChatMessageCallback::class.java ) { listeners ->
			PlayerChatMessageCallback { player, packet ->
				for ( listener in listeners ) {
					val result = listener.interact( player, packet )
					if ( result != ActionResult.PASS ) return@PlayerChatMessageCallback result
				}

				return@PlayerChatMessageCallback ActionResult.PASS
			}
		}
	}

	/**
	 * Runs when a player has sent a chat message.
	 * @param player The player who sent the chat message.
	 * @param packet Data about the chat message. Includes content, signature, etc.
	 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
	 * @see PlayerChatMessageCallback
	 * @since 0.2.0
	 */
	fun interact( player: ServerPlayerEntity, packet: ChatMessageC2SPacket ): ActionResult
}
