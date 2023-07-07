package com.viral32111.events.listener.server

import com.viral32111.events.Main
import com.viral32111.events.text
import com.viral32111.events.type
import net.minecraft.advancement.Advancement
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

/**
 * Runs when a player has completed an advancement (including challenges and goals).
 * @param player The player who completed the advancement.
 * @param advancement The advancement that was completed.
 * @param criterionName The name of the criterion that triggered the advancement completion.
 * @param shouldAnnounceToChat If this announcement should be publicly announced in chat.
 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
 * @see com.viral32111.events.callback.server.PlayerCompleteAdvancementCallback
 * @since 0.3.6
 */
fun playerCompleteAdvancementCallbackListener( player: ServerPlayerEntity, advancement: Advancement, criterionName: String, shouldAnnounceToChat: Boolean ): ActionResult {

	// The player's username and unique identifier.
	val playerName = player.name.string
	val playerUUID = player.uuidAsString

	// The advancement's information.
	// Take a look at the extension methods in AdvancementFrameExtensions.kt!
	val advancementName = advancement.display?.title
	val advancementText = advancement.text
	val advancementType = advancement.type

	// Display a console message with details of this event.
	Main.LOGGER.info( "Player '$playerName' ($playerUUID) $advancementText '$advancementName' (Criterion: '$criterionName', Type: '$advancementType', Announce: '$shouldAnnounceToChat')." )

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}
