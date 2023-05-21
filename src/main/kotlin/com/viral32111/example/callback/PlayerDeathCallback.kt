package com.viral32111.example.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

fun interface PlayerDeathCallback {
	companion object {
		val EVENT: Event<PlayerDeathCallback> = EventFactory.createArrayBacked( PlayerDeathCallback::class.java ) { listeners ->
			PlayerDeathCallback { player, damageSource ->
				for ( listener in listeners ) {
					val result = listener.interact( player, damageSource )
					if ( result != ActionResult.PASS ) return@PlayerDeathCallback result
				}

				return@PlayerDeathCallback ActionResult.PASS
			}
		}
	}

	fun interact( player: ServerPlayerEntity, damageSource: DamageSource ): ActionResult
}
