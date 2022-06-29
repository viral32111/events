package com.viral32111.example.mixin;

import com.viral32111.example.Example;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.RequestChatPreviewC2SPacket;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;

@Mixin( ServerPlayNetworkHandler.class )
public class ServerPlayNetworkHandlerMixin {

	// The player these events are for
	@Shadow
	public ServerPlayerEntity player;

	// Runs when a player sends a message in chat.
	// NOTE: Does not include properties for message signatures, but will still run for signed messages.
	@Inject( method = "handleMessage", at = @At( "TAIL" ) )
	private void handleMessage( ChatMessageC2SPacket packet, FilteredMessage<String> message, CallbackInfo callbackInfo ) {

		// The player's account username and unique identifier.
		String playerName = player.getName().getString();
		String playerUUID = player.getUuidAsString();

		// The content of the chat message.
		String messageContent = message.raw();

		// Print a message to the server's console with details of this event.
		Example.LOGGER.info( String.format( "Player '%s' (%s) sent normal chat message '%s'.", playerName, playerUUID, messageContent ) );

	}

	// Runs when a player sends a message in chat.
	// NOTE: Includes properties for message signatures, but will still run for unsigned messages.
	@Inject( method = "handleDecoratedMessage", at = @At( "TAIL" ) )
	private void handleDecoratedMessage( FilteredMessage<SignedMessage> message, CallbackInfo callbackInfo ) {

		// The player's account username and unique identifier.
		String playerName = player.getName().getString();
		String playerUUID = player.getUuidAsString();

		// The content of the chat message.
		String messageContent = message.raw().getContent().getString();

		// Useful details about the signed message.
		boolean signatureExists = message.raw().signature().saltSignature().isSignaturePresent(); // Has this message been signed?
		String signatureContent = message.raw().signedContent().getString(); // The message that was signed (this should be the same as messageContent above).
		String signatureAuthorUUID = message.raw().signature().sender().toString(); // Unique identifier of the player that sent (and thus signed) the message (this should be the same as playerUUID above).
		Instant signatureTimestamp = message.raw().signature().timestamp(); // Precise date & time of when the message was signed.

		// Print a message to the server's console with details of this event.
		Example.LOGGER.info( String.format( "Player '%s' (%s) sent decorated chat message '%s' (Is Signed: %s, Signed By: %s) at '%s'.", playerName, playerUUID, messageContent, ( signatureExists ? "Yes" : "No" ), signatureAuthorUUID, signatureTimestamp.toString() ) );

	}

	// Runs when a player types a character in their chatbox.
	// NOTE: Does not run if the player disables chat previews on their client, or if the server has chat previews disabled.
	@Inject( method = "onRequestChatPreview", at = @At( "RETURN" ) )
	private void onRequestChatPreview( RequestChatPreviewC2SPacket packet, CallbackInfo callbackInfo ) {

		// The player's account username and unique identifier.
		String playerName = player.getName().getString();
		String playerUUID = player.getUuidAsString();

		// The content of the chat message so far.
		String previewContent = packet.query();

		// Print a message to the server's console with details of this event.
		Example.LOGGER.info( String.format( "Player '%s' (%s) typed '%s' in chat.", playerName, playerUUID, previewContent ) );

	}

}
