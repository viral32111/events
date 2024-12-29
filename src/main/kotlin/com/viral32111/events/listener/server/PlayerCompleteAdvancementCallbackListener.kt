package com.viral32111.events.listener.server

import com.viral32111.events.Main
import com.viral32111.events.getName
import com.viral32111.events.getText
import com.viral32111.events.getType
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
fun playerCompleteAdvancementCallbackListener(
	player: ServerPlayerEntity,
	advancement: Advancement,
	criterionName: String,
	shouldAnnounceToChat: Boolean
): ActionResult {

	// The player's username and unique identifier.
	val playerName = player.name.string
	val playerUUID = player.uuidAsString

	// The advancement's information.
	// NOTE: Take a look at the extension methods in AdvancementFrameExtensions.kt!
	val advancementName = advancement.getName()
	val advancementText = advancement.getText()
	val advancementType = advancement.getType()

	// Display a console message with details of this event.
	if ((advancementName != null && advancementText != null && advancementType != null) && shouldAnnounceToChat)
		Main.LOGGER.info("Player '$playerName' ($playerUUID) $advancementText '$advancementName' (criterion: '$criterionName', type: '$advancementType', announce: '$shouldAnnounceToChat').")

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}
