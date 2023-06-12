package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult

fun interface PlayerGainExperienceCallback {
	companion object {
		val EVENT: Event<PlayerGainExperienceCallback> = EventFactory.createArrayBacked( PlayerGainExperienceCallback::class.java ) { listeners ->
			PlayerGainExperienceCallback { player, experience ->
				for ( listener in listeners ) {
					val result = listener.interact( player, experience )
					if ( result != ActionResult.PASS ) return@PlayerGainExperienceCallback result
				}

				return@PlayerGainExperienceCallback ActionResult.PASS
			}
		}
	}

	fun interact( player: PlayerEntity, experience: Int ): ActionResult
}
