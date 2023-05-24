package com.viral32111.events.listener.server

import com.viral32111.events.Main
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

/**
 * Runs when a player has left the server.
 * @param player The player that left the server.
 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
 * @see com.viral32111.events.callback.server.PlayerCanJoinCallback
 * @since 0.2.0
 */
fun playerLeaveCallbackListener( player: ServerPlayerEntity ): ActionResult {

	// The player's username and unique identifier.
	val playerName = player.name.string
	val playerUUID = player.uuidAsString

	// The player's position (where they logged out).
	val playerPosX = player.pos.getX()
	val playerPosY = player.pos.getY()
	val playerPosZ = player.pos.getZ()

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) left (at 74, 63, -136).
	Main.LOGGER.info( "Player '%s' (%s) left (at %.0f, %.0f, %.0f).".format( playerName, playerUUID, playerPosX, playerPosY, playerPosZ ) )

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}
