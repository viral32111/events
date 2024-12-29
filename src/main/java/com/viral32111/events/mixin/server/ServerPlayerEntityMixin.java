package com.viral32111.events.mixin.server;

import com.viral32111.events.callback.server.PlayerDeathCallback;
import com.viral32111.events.callback.server.PlayerEnterPortalCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

	/**
	 * Runs when a player has died.
	 *
	 * @param damageSource Data about the death. Includes attacker, source, etc.
	 * @see com.viral32111.events.callback.server.PlayerDeathCallback
	 * @since 0.1.0
	 */
	@Inject(method = "onDeath", at = @At("TAIL"))
	private void onDeath(DamageSource damageSource, CallbackInfo info) {

		// Cast the current object (within the injection) to a player.
		// NOTE: Must first cast to generic object otherwise it is invalid.
		ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

		// Invoke all listeners of this mixin's callback.
		// No need to check the listener results as this mixin cannot be cancelled.
		PlayerDeathCallback.Companion.getEVENT().invoker().interact(player, damageSource);

	}

	/**
	 * Runs before a player travels through a portal.
	 *
	 * @param destinationWorld The world the target dimension is in.
	 * @see com.viral32111.events.callback.server.PlayerEnterPortalCallback
	 * @see 0.3.4
	 */
	@Inject(method = "moveToWorld", at = @At(value = "HEAD"), cancellable = true)
	public void moveToWorld(ServerWorld destinationWorld, CallbackInfoReturnable<Entity> callbackInfo) {

		// Cast the current object (within the injection) to a player.
		// NOTE: Must first cast to generic object otherwise it is invalid.
		ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

		// Invoke all listeners of this mixin's callback.
		ActionResult actionResult = PlayerEnterPortalCallback.Companion.getEVENT().invoker().interact(player, destinationWorld);

		// Prevent the player from travelling dimension if any of the listeners return a failure.
		if (actionResult == ActionResult.FAIL) {
			callbackInfo.setReturnValue(player);
		}
	}

}
