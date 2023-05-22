package com.viral32111.example.api.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

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

	fun interact( player: ServerPlayerEntity, packet: ChatMessageC2SPacket ): ActionResult
}
