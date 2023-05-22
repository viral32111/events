package com.viral32111.example.listener

import com.viral32111.example.Example.Companion.LOGGER
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import java.util.*

// Runs when a player has died.
fun playerDeathCallbackListener( player: ServerPlayerEntity, damageSource: DamageSource ): ActionResult {

	// Store the source & attacker of the player's death.
	// NOTE: These can be null if the death was not caused by another entity (i.e., falling, /kill).
	val source = damageSource.source // e.g., Arrow
	val attacker = damageSource.attacker // e.g., Skeleton

	// The player's username and unique identifier.
	val playerName = player.name.string
	val playerUUID = player.uuidAsString

	// The player's position at the time of death.
	val playerPosX = player.pos.getX()
	val playerPosY = player.pos.getY()
	val playerPosZ = player.pos.getZ()

	// The reason for the player's death.
	// NOTE: This is not the same as the death message! Use 'damageSource.getDeathMessage( player ).getString()' for that.
	var deathReason: String = damageSource.name.uppercase( Locale.getDefault() )
	if ( source != null && attacker != null ) {
		val sourceName = source.name.string
		val attackerName = attacker.name.string

		// Sometimes the source & attacker are identical (e.g., Creepers), so no point in displaying both.
		deathReason = if ( sourceName == attackerName ) attackerName else "%s of %s".format( sourceName, attackerName )
	}

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to OUTOFWORLD, at 62, 63, -136.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to Arrow of Skeleton, at 62, 64, -131.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to Creeper, at 68, 63, -141.
	LOGGER.info( "Player '%s' (%s) died due to %s, at %.0f, %.0f, %.0f.".format( playerName, playerUUID, deathReason, playerPosX, playerPosY, playerPosZ ) )

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}