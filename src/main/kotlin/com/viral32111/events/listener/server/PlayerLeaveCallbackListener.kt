package com.viral32111.events.listener.server

import com.viral32111.events.Main
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import kotlin.math.roundToInt

/**
 * Runs when a player has left the server.
 * @param player The player that left the server.
 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
 * @see com.viral32111.events.callback.server.PlayerCanJoinCallback
 * @since 0.2.0
 */
fun playerLeaveCallbackListener(player: Player): InteractionResult {

	// The player's username and unique identifier.
	val playerName = player.name.string
	val playerUUID = player.stringUUID

	// The player's position (where they logged out).
	val playerPosX = player.x.roundToInt()
	val playerPosY = player.y.roundToInt()
	val playerPosZ = player.z.roundToInt()

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) left (at 74, 63, -136).
	Main.LOGGER.info("Player '$playerName' ($playerUUID) left at [$playerPosX, $playerPosY, $playerPosZ].")

	// Pass to allow other listeners to execute.
	return InteractionResult.PASS

}
