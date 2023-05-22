package com.viral32111.example.api.mixin;

import com.viral32111.example.api.callback.PlayerDeathCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( ServerPlayerEntity.class )
public class ServerPlayerEntityMixin {

	// Runs when a player has died.
	@Inject( method = "onDeath", at = @At( "TAIL" ) )
	private void onDeath( DamageSource damageSource, CallbackInfo info ) {

		// Cast the current object (within the injection) to a player.
		// NOTE: Must first cast to generic object otherwise it is invalid.
		ServerPlayerEntity player = ( ServerPlayerEntity ) ( Object ) this;

		// Invoke all listeners of this mixin's callback
		// No need to check the listener results as this mixin cannot be cancelled
		PlayerDeathCallback.Companion.getEVENT().invoker().interact( player, damageSource );

	}

}
