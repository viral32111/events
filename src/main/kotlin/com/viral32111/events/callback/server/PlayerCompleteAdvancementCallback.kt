package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.advancements.Advancement
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player

/**
 * Callback for after a player completes an advancement (including challenges and goals).
 * @see com.viral32111.events.mixin.server.PlayerAdvancementTrackerMixin.grantCriterion
 * @see com.viral32111.events.listener.server.playerCompleteAdvancementCallbackListener
 * @since 0.3.6
 */
fun interface PlayerCompleteAdvancementCallback {
	companion object {
		val EVENT: Event<PlayerCompleteAdvancementCallback> =
			EventFactory.createArrayBacked(PlayerCompleteAdvancementCallback::class.java) { listeners ->
				PlayerCompleteAdvancementCallback { player, advancement, criterionName, shouldAnnounceToChat ->
					for (listener in listeners) {
						val result = listener.interact(player, advancement, criterionName, shouldAnnounceToChat)
						if (result != InteractionResult.PASS) return@PlayerCompleteAdvancementCallback result
					}

					return@PlayerCompleteAdvancementCallback InteractionResult.PASS
				}
			}
	}

	/**
	 * Runs when a player has completed an advancement (including challenges and goals).
	 * @param player The player who completed the advancement.
	 * @param advancement The advancement that was completed.
	 * @param criterionName The name of the criterion that triggered the advancement completion.
	 * @param shouldAnnounceToChat If this announcement should be publicly announced in chat.
	 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
	 * @see PlayerChatMessageCallback
	 * @since 0.3.6
	 */
	fun interact(
		player: Player,
		advancement: Advancement,
		criterionName: String,
		shouldAnnounceToChat: Boolean
	): InteractionResult
}
