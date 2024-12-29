package com.viral32111.events.listener.server

import com.viral32111.events.Main
import net.minecraft.entity.damage.DamageSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import java.util.*
import kotlin.math.roundToInt

/**
 * Runs when a player has died.
 * @param player The player that died.
 * @param damageSource Data about the death. Includes attacker, source, etc.
 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
 * @see com.viral32111.events.callback.server.PlayerCanJoinCallback
 * @since 0.2.0
 */
fun playerDeathCallbackListener(player: ServerPlayerEntity, damageSource: DamageSource): ActionResult {

	// Store the source & attacker of the player's death.
	// NOTE: These can be null if the death was not caused by another entity (i.e., falling, /kill).
	val source = damageSource.source // e.g., Arrow
	val attacker = damageSource.attacker // e.g., Skeleton

	// The player's username and unique identifier.
	val playerName = player.name.string
	val playerUUID = player.uuidAsString

	// The player's position at the time of death.
	val playerPosX = player.pos.getX().roundToInt()
	val playerPosY = player.pos.getY().roundToInt()
	val playerPosZ = player.pos.getZ().roundToInt()

	// The reason for the player's death.
	// NOTE: This is not the same as the death message! Use 'damageSource.getDeathMessage( player ).getString()' for that.
	var deathReason: String = damageSource.name.uppercase(Locale.getDefault())
	if (source != null && attacker != null) {
		val sourceName = source.name.string
		val attackerName = attacker.name.string

		// Sometimes the source & attacker are identical (e.g., Creepers), so no point in displaying both.
		deathReason = if (sourceName == attackerName) attackerName else "%s of %s".format(sourceName, attackerName)
	}

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to OUTOFWORLD, at 62, 63, -136.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to Arrow of Skeleton, at 62, 64, -131.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to Creeper, at 68, 63, -141.
	Main.LOGGER.info("Player '$playerName' ($playerUUID) died due to '$deathReason' at [$playerPosX, $playerPosY, $playerPosZ].")

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}
