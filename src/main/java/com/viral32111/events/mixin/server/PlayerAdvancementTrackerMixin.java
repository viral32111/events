package com.viral32111.events.mixin.server;

import com.viral32111.events.callback.server.PlayerCompleteAdvancementCallback;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin( PlayerAdvancementTracker.class )
public class PlayerAdvancementTrackerMixin {

	@Shadow
	private ServerPlayerEntity owner;

	@SuppressWarnings( "SameReturnValue" )
	@Shadow public AdvancementProgress getProgress( AdvancementEntry advancement ) { return null; }

	/**
	 * Runs when a player gains progress towards an advancement.
	 * @param advancement The advancement that was progressed.
	 * @param criterionName The name of the criterion that was progressed.
	 * @see com.viral32111.events.callback.server.PlayerCompleteAdvancementCallback
	 * @since 0.3.6
	 */
	@Inject( method = "grantCriterion", at = @At( "RETURN" ) )
	private void grantCriterion( AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> info ) {

		// Do not continue if this advancement is incomplete.
		AdvancementProgress advancementProgress = this.getProgress( advancement );
		if ( !advancementProgress.isDone() ) return;

		// Determine if this announcement would be publicly announced in chat, taking into account game-rules and the advancement itself.
		boolean shouldAnnounceToChat = ( advancement.value().display().isPresent() && advancement.value().display().get().shouldAnnounceToChat() ) && owner.getWorld().getGameRules().getBoolean( GameRules.ANNOUNCE_ADVANCEMENTS );

		// Invoke all listeners of this mixin's callback.
		// No need to check the listener results as this mixin cannot be cancelled.
		PlayerCompleteAdvancementCallback.Companion.getEVENT().invoker().interact( owner, advancement.value(), criterionName, shouldAnnounceToChat );

	}

}
