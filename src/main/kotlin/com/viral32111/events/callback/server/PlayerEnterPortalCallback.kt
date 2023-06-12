package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult

fun interface PlayerEnterPortalCallback {
	companion object {
		val EVENT: Event<PlayerEnterPortalCallback> = EventFactory.createArrayBacked( PlayerEnterPortalCallback::class.java ) { listeners ->
			PlayerEnterPortalCallback { player, destinationWorld ->
				for ( listener in listeners ) {
					val result = listener.interact( player, destinationWorld )
					if ( result != ActionResult.PASS ) return@PlayerEnterPortalCallback result
				}

				return@PlayerEnterPortalCallback ActionResult.PASS
			}
		}
	}

	fun interact( player: ServerPlayerEntity, destinationWorld: ServerWorld ): ActionResult
}
