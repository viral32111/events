package com.viral32111.example.listener

import com.viral32111.example.Example.Companion.LOGGER
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

// Runs when a player leaves the server.
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
	LOGGER.info( "Player '%s' (%s) left (at %.0f, %.0f, %.0f).".format( playerName, playerUUID, playerPosX, playerPosY, playerPosZ ) )

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}
