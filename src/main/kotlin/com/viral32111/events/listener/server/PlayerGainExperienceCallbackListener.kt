package com.viral32111.events.listener.server

import com.viral32111.events.Main
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player

/**
 * Runs when a player has gained experience.
 * @param player The player that gained experience.
 * @param experience The amount of experience gained.
 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
 * @see com.viral32111.events.callback.server.PlayerGainExperienceCallback
 * @since 0.3.4
 */
fun playerGainExperienceCallbackListener(player: Player, experience: Int): InteractionResult {

	// The player's username & unique identifier.
	val playerName = player.name.string
	val playerUUID = player.stringUUID

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) gained 12 experience.
	Main.LOGGER.info("Player '$playerName' ($playerUUID) gained $experience experience.")

	// Pass to allow other listeners to execute.
	return InteractionResult.PASS

}
