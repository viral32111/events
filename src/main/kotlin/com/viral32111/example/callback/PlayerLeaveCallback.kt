package com.viral32111.example.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

fun interface PlayerLeaveCallback {
	companion object {
		val EVENT: Event<PlayerLeaveCallback> = EventFactory.createArrayBacked( PlayerLeaveCallback::class.java ) { listeners ->
			PlayerLeaveCallback { player ->
				for ( listener in listeners ) {
					val result = listener.interact( player )
					if ( result != ActionResult.PASS ) return@PlayerLeaveCallback result
				}

				return@PlayerLeaveCallback ActionResult.PASS
			}
		}
	}

	fun interact( player: ServerPlayerEntity ): ActionResult
}
