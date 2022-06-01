package com.viral32111.example.mixin;

import com.viral32111.example.Example;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( ServerPlayerEntity.class )
public class ServerPlayerEntityMixin {

	// Runs when a player dies...
	@Inject( method = "onDeath", at = @At( "TAIL" ) )
	private void onDeath( DamageSource damageSource, CallbackInfo info ) {
		ServerPlayerEntity player = ( ServerPlayerEntity ) ( Object ) this;

		Example.LOGGER.info( player.getName().getString() );

		/*if ( damageSource.getAttacker() == null ) {
			Example.LOGGER.debug( "Attacker is null!" );
		} else {
			Example.LOGGER.debug( "Attacker is NOT null!" );
		}

		if ( damageSource.getSource() == null ) {
			Example.LOGGER.debug( "Source is null!" );
		} else {
			Example.LOGGER.debug( "Source is NOT null!" );
		}*/

		//String targetName = Objects.requireNonNull( damageSource.getSource() ).getName().getString();
		//String targetUUID = Objects.requireNonNull( damageSource.getSource() ).getUuidAsString();

		//String attackerName = Objects.requireNonNull( damageSource.getAttacker() ).getName().getString();
		//String attackerUUID = Objects.requireNonNull( damageSource.getAttacker() ).getUuidAsString();

		//Example.LOGGER.info( String.format( "Player '%s' died.", damageSource.getAttacker().getName().getString() ) );
	}

}
