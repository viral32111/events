package com.viral32111.events.listener.server

import com.viral32111.events.Main
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.world.TeleportTarget
import kotlin.math.roundToInt

/**
 * Runs before a player is teleported to another dimension via a Nether or End portal.
 * @param player The player that is about to be teleported via a portal.
 * @param teleportTarget The position & dimension the player will be teleported to.
 * @return Pass to allow the player to be teleported, fail to prevent the player from being teleported.
 * @see com.viral32111.events.callback.server.PlayerEnterPortalCallback
 * @since 0.3.4
 */
fun playerEnterPortalCallbackListener(player: ServerPlayerEntity, teleportTarget: TeleportTarget): ActionResult {

	// The player's username & unique identifier.
	val playerName = player.name.string
	val playerUUID = player.uuidAsString

	// The player's position at the time of travelling.
	val playerPosX = player.pos.getX().roundToInt()
	val playerPosY = player.pos.getY().roundToInt()
	val playerPosZ = player.pos.getZ().roundToInt()

	// The player's current dimension & dimension they are travelling to.
	val currentDimension = player.serverWorld.registryKey
	val currentDimensionName = "${currentDimension.value.namespace}:${currentDimension.value.path}"
	val destinationDimension = teleportTarget.world.registryKey
	val destinationDimensionName = "${destinationDimension.value.namespace}:${destinationDimension.value.path}"

	// The player's position in the dimension they are travelling to.
	val destinationPosX = teleportTarget.position.x.roundToInt()
	val destinationPosY = teleportTarget.position.y.roundToInt()
	val destinationPosZ = teleportTarget.position.z.roundToInt()

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) is travelling from dimension 'minecraft:overworld' (at [62, 63, -135]) to dimension 'minecraft:the_nether' (at [62, 63, -135]).
	Main.LOGGER.info("Player '$playerName' ($playerUUID) is travelling from dimension '$currentDimensionName' (at [$playerPosX, $playerPosY, $playerPosZ]) to dimension '$destinationDimensionName' (at [$destinationPosX, $destinationPosY, $destinationPosZ]).")

	// Pass to allow other listeners to execute.
	// ActionResult.FAIL will prevent the player from joining (and other listeners from executing).
	return ActionResult.PASS

}
