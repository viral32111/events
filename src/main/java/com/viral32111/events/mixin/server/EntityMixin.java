package com.viral32111.events.mixin.server;

import com.viral32111.events.callback.server.PlayerEnterPortalCallback;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.dimension.PortalManager;
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
	private TeleportTarget teleportTarget = null;

	/**
	 * Finds the ServerPlayerEntity from this Entity.
	 * @return The ServerPlayerEntity, or null if this Entity is not a player.
	 * @since 0.5.0
	 */
	@Unique
	@Nullable
	private ServerPlayerEntity asServerPlayer() {

		// Cast the current object (within the injection) to an entity.
		// NOTE: Must first cast to generic object otherwise it is invalid.
		Entity entity = (Entity) (Object) this;

		// Do not continue if the entity isn't a player
		if (!entity.isPlayer()) return null;

		// Get the server-side player instance
		// TODO: Not sure if we can safely cast Entity to ServerPlayerEntity here!
		MinecraftServer server = entity.getServer();
		if (server == null) return null;
		PlayerManager playerManager = server.getPlayerManager();
		return playerManager.getPlayer(entity.getUuid());

	}

	/**
	 * Captures the result of the call to 'this.portalManager.createTeleportTarget(serverWorld, this)' within Entity.tickPortalTeleportation().
	 * @param instance The portal manager instance associated with the entity.
	 * @param serverWorld The world the teleportation will happen in.
	 * @param entity The entity to be teleported.
	 * <a href="https://gist.github.com/TelepathicGrunt/3784f8a8b317bac11039474012de5fb4#commonly-used-kinds-of-mixins">Redirect mixins can be dangerous!</a>
	 * @since 0.5.0
	 */
	@Redirect(method = "tickPortalTeleportation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/PortalManager;createTeleportTarget(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)Lnet/minecraft/world/TeleportTarget;"))
	private TeleportTarget createTeleportTarget(PortalManager instance, ServerWorld serverWorld, Entity entity) {
		this.teleportTarget = instance.createTeleportTarget(serverWorld, entity);
		return this.teleportTarget;
	}

	/**
	 * Runs before an entity is teleported to another dimension via a Nether or End portal.
	 * @see com.viral32111.events.callback.server.PlayerEnterPortalCallback
	 * @since 0.5.0
	 */
	@Inject(method = "tickPortalTeleportation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/PortalManager;createTeleportTarget(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)Lnet/minecraft/world/TeleportTarget;", shift = At.Shift.AFTER), cancellable = true)
	private void tickPortalTeleportation(CallbackInfo callbackInfo) {

		// Is this a player travelling through a portal?
		ServerPlayerEntity player = this.asServerPlayer();
		if (player != null && this.teleportTarget != null) {
			// Invoke all listeners of this mixin's callback.
			ActionResult actionResult = PlayerEnterPortalCallback.Companion.getEVENT().invoker().interact(player, this.teleportTarget);

			// Prevent the player from travelling dimension if any of the listeners return a failure.
			if (actionResult == ActionResult.FAIL) callbackInfo.cancel();

			// Clear the teleport target so we're fresh for the next invocation
			this.teleportTarget = null;
		}

	}

}
