package com.viral32111.example.api.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.ClientConnection
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

fun interface PlayerJoinCallback {
	companion object {
		val EVENT: Event<PlayerJoinCallback> = EventFactory.createArrayBacked( PlayerJoinCallback::class.java ) { listeners ->
			PlayerJoinCallback { connection, player ->
				for ( listener in listeners ) {
					val result = listener.interact( connection, player )
					if ( result != ActionResult.PASS ) return@PlayerJoinCallback result
				}

				return@PlayerJoinCallback ActionResult.PASS
			}
		}
	}

	fun interact( connection: ClientConnection, player: ServerPlayerEntity ): ActionResult
}
