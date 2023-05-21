package com.viral32111.example.mixin;

import com.viral32111.example.Example;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( ServerPlayNetworkHandler.class )
public class ServerPlayNetworkHandlerMixin {

	// The player these events are for
	@Shadow
	public ServerPlayerEntity player;

	// Runs when a player has sent a message in chat.
	// NOTE: This will run for signed & unsigned messages, but signed messages will have .signature()-related properties.
	@Inject( method = "onChatMessage", at = @At( "TAIL" ) )
	private void onChatMessage( ChatMessageC2SPacket packet, CallbackInfo callbackInfo ) {

		// The player's account username and unique identifier.
		String playerName = player.getName().getString();
		String playerUUID = player.getUuidAsString();

		// The content of the chat message.
		String messageContent = packet.chatMessage();

		// Is the message signed?
		boolean isSigned = packet.signature() != null && packet.signature().data().length > 0;

		// Print a message to the server's console with details of this event.
		Example.Companion.getLOGGER().info( String.format( "Player '%s' (%s) sent chat message '%s' (Signed: %b).", playerName, playerUUID, messageContent, isSigned ) );

	}

}
