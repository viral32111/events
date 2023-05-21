package com.viral32111.example.mixins;

import com.viral32111.example.Example;
import net.minecraft.entity.Entity;
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
		// NOTE: Must cast to generic object first otherwise the cast is invalid.
		ServerPlayerEntity player = ( ServerPlayerEntity ) ( Object ) this;

		// Store the attacker and source of the player's death.
		// NOTE: These can be null if the death was not caused by another entity (i.e., falling, /kill).
		Entity source = damageSource.getSource(); // e.g., Arrow
		Entity attacker = damageSource.getAttacker(); // e.g., Skeleton

		// The player's account username and unique identifier.
		String playerName = player.getName().getString();
		String playerUUID = player.getUuidAsString();

		// The player's current position at the time of death.
		double playerPosX = player.getPos().getX();
		double playerPosY = player.getPos().getY();
		double playerPosZ = player.getPos().getZ();

		// The reason for the player's death.
		// NOTE: This is not the same as the death message! Use 'damageSource.getDeathMessage( player ).getString()' for that.
		String deathReason = damageSource.getName().toUpperCase();
		if ( source != null && attacker != null ) {
			String sourceName = source.getName().getString();
			String attackerName = attacker.getName().getString();

			// Sometimes the source and attacker are identical (e.g., Creepers), so no use displaying both.
			if ( sourceName.equals( attackerName ) ) {
				deathReason = attackerName;
			} else {
				deathReason = String.format( "%s of %s", source.getName().getString(), attacker.getName().getString() );
			}
		}

		// Print a message to the server's console with details of this event.
		Example.INSTANCE.getLOGGER().info( String.format( "Player '%s' (%s) died due to %s, at %.0f, %.0f, %.0f.", playerName, playerUUID, deathReason, playerPosX, playerPosY, playerPosZ ) );

		/* Examples:
		 - Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to OUTOFWORLD, at 62, 63, -136.
		 - Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to Arrow of Skeleton, at 62, 64, -131.
		 - Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) died due to Creeper, at 68, 63, -141.
		 */

	}

}
