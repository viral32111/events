package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player

fun interface PlayerGainExperienceCallback {
	companion object {
		val EVENT: Event<PlayerGainExperienceCallback> =
			EventFactory.createArrayBacked(PlayerGainExperienceCallback::class.java) { listeners ->
				PlayerGainExperienceCallback { player, experience ->
					for (listener in listeners) {
						val result = listener.interact(player, experience)
						if (result != InteractionResult.PASS) return@PlayerGainExperienceCallback result
					}

					return@PlayerGainExperienceCallback InteractionResult.PASS
				}
			}
	}

	fun interact(player: Player, experience: Int): InteractionResult
}
