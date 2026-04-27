package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.player.Player

/**
 * Callback for after a player dies.
 * @see com.viral32111.events.mixin.server.PlayerEntityMixin.onDeath
 * @see com.viral32111.events.listener.server.playerDeathCallbackListener
 * @since 0.2.0
 */
fun interface PlayerDeathCallback {
	companion object {
		val EVENT: Event<PlayerDeathCallback> =
			EventFactory.createArrayBacked(PlayerDeathCallback::class.java) { listeners ->
				PlayerDeathCallback { player, damageSource ->
					for (listener in listeners) {
						val result = listener.interact(player, damageSource)
						if (result != InteractionResult.PASS) return@PlayerDeathCallback result
					}

					return@PlayerDeathCallback InteractionResult.PASS
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
	fun interact(player: Player, damageSource: DamageSource): InteractionResult
}
