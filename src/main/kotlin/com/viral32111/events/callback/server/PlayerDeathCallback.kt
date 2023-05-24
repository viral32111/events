package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

/**
 * Callback for after a player dies.
 * @see com.viral32111.events.mixin.server.ServerPlayerEntityMixin.onDeath
 * @since 0.2.0
 */
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

	/**
	 * Runs when a player has died.
	 * @param player The player that died.
	 * @param damageSource Data about the death. Includes attacker, source, etc.
	 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
	 * @see PlayerDeathCallback
	 * @since 0.2.0
	 */
	fun interact( player: ServerPlayerEntity, damageSource: DamageSource ): ActionResult
}
