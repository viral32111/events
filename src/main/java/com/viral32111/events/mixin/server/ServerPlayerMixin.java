package com.viral32111.events.mixin.server;

import com.viral32111.events.callback.server.PlayerDeathCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	/**
	 * Runs when a player has died.
	 * @param damageSource Data about the death. Includes attacker, source, etc.
	 * @see com.viral32111.events.callback.server.PlayerDeathCallback
	 * @since 0.1.0
	 */
	@Inject(method = "onDeath", at = @At("TAIL"))
	private void viral32111_events_onDeath(DamageSource damageSource, CallbackInfo info) {

		// Cast the current object (within the injection) to a player.
		// NOTE: Must first cast to generic object otherwise it is invalid.
		ServerPlayer player = (ServerPlayer) (Object) this;

		// Invoke all listeners of this mixin's callback.
		// No need to check the listener results as this mixin cannot be cancelled.
		PlayerDeathCallback.Companion.getEVENT().invoker().interact(player, damageSource);

	}

}
