package com.viral32111.events.mixin.server;

import com.viral32111.events.callback.server.PlayerGainExperienceCallback;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( PlayerEntity.class )
public class PlayerEntityMixin {

	/**
	 * Runs when a player has gained experience.
	 * @param experience The amount of experience gained.
	 * @see com.viral32111.events.callback.server.PlayerGainExperienceCallback
	 * @since 0.3.4
	 */
	@Inject( method = "addExperience", at = @At( "TAIL" ) )
	public void addExperience( int experience, CallbackInfo info ) {

		// Ignore this event if the player did not actually gain any experience (why does this happen?)
		if ( experience <= 0 ) return;

		// Cast the current object (within the injection) to a player.
		// NOTE: Must first cast to generic object otherwise it is invalid.
		PlayerEntity player = ( PlayerEntity ) ( Object ) this;

		// Invoke all listeners of this mixin's callback.
		// No need to check the listener results as this mixin cannot be cancelled.
		PlayerGainExperienceCallback.Companion.getEVENT().invoker().interact( player, experience );

	}

}
