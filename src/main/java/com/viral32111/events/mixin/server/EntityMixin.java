package com.viral32111.events.mixin.server;

import com.viral32111.events.callback.server.PlayerEnterPortalCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PortalProcessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

	/**
	 * The redirected createTeleportTarget() result for the next tickPortalTeleportation() invocation.
	 * @since 0.5.0
	 */
	@Unique
	private TeleportTransition viral32111_events_teleportTarget = null;

	/**
	 * Finds the PlayerEntity from this Entity.
	 * @return The PlayerEntity, or null if this Entity is not a player.
	 * @since 0.5.0
	 */
	@Unique
	@Nullable
	private Player viral32111_events_asServerPlayer() {

		// Cast the current object (within the injection) to an entity.
		// NOTE: Must first cast to generic object otherwise it is invalid.
		Entity entity = (Entity) (Object) this;

		// Do not continue if the entity isn't a player
		if (!(entity instanceof Player)) return null;

		// Get the server-side player instance
		// TODO: Not sure if we can safely cast Entity to PlayerEntity here!
		MinecraftServer server = entity.level().getServer();
		if (server == null) return null;
		PlayerList playerManager = server.getPlayerList();
		return playerManager.getPlayer(entity.getUUID());

	}

	/**
	 * Captures the result of the call to 'this.portalManager.createTeleportTarget(serverWorld, this)' within Entity.tickPortalTeleportation().
	 * @param instance The portal manager instance associated with the entity.
	 * @param serverWorld The world the teleportation will happen in.
	 * @param entity The entity to be teleported.
	 * @since 0.5.0
	 */
	@Redirect(method = "tickPortalTeleportation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/PortalManager;createTeleportTarget(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)Lnet/minecraft/world/TeleportTarget;"))
	private TeleportTransition viral32111_events_createTeleportTarget(PortalProcessor instance, ServerLevel serverWorld, Entity entity) {
		this.viral32111_events_teleportTarget = instance.getPortalDestination(serverWorld, entity);
		return this.viral32111_events_teleportTarget;
	}

	/**
	 * Runs before an entity is teleported to another dimension via a Nether or End portal.
	 * @see com.viral32111.events.callback.server.PlayerEnterPortalCallback
	 * @since 0.5.0
	 */
	@Inject(method = "tickPortalTeleportation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/PortalManager;createTeleportTarget(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)Lnet/minecraft/world/TeleportTarget;", shift = At.Shift.AFTER), cancellable = true)
	private void viral32111_events_tickPortalTeleportation(CallbackInfo callbackInfo) {

		// Is this a player travelling through a portal?
		Player player = this.viral32111_events_asServerPlayer();
		if (player != null && this.viral32111_events_teleportTarget != null) {
			// Invoke all listeners of this mixin's callback.
			InteractionResult actionResult = PlayerEnterPortalCallback.Companion.getEVENT().invoker().interact(player, this.viral32111_events_teleportTarget);

			// Prevent the player from travelling dimension if any of the listeners return a failure.
			if (actionResult == InteractionResult.FAIL) callbackInfo.cancel();

			// Clear the teleport target so we're fresh for the next invocation
			this.viral32111_events_teleportTarget = null;
		}

	}

}
