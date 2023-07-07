package com.viral32111.events.callback.server

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.advancement.Advancement
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

/**
 * Callback for after a player completes an advancement (including challenges and goals).
 * @see com.viral32111.events.mixin.server.PlayerAdvancementTrackerMixin.grantCriterion
 * @see com.viral32111.events.listener.server.playerCompleteAdvancementCallbackListener
 * @since 0.3.6
 */
fun interface PlayerCompleteAdvancementCallback {
	companion object {
		val EVENT: Event<PlayerCompleteAdvancementCallback> = EventFactory.createArrayBacked( PlayerCompleteAdvancementCallback::class.java ) { listeners ->
			PlayerCompleteAdvancementCallback { player, advancement, criterionName, shouldAnnounceToChat ->
				for ( listener in listeners ) {
					val result = listener.interact( player, advancement, criterionName, shouldAnnounceToChat )
					if ( result != ActionResult.PASS ) return@PlayerCompleteAdvancementCallback result
				}

				return@PlayerCompleteAdvancementCallback ActionResult.PASS
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
	fun interact( player: ServerPlayerEntity, advancement: Advancement, criterionName: String, shouldAnnounceToChat: Boolean ): ActionResult
}
