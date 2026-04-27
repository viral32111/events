package com.viral32111.events.mixin.server;

import com.viral32111.events.callback.server.PlayerChatMessageCallback;
import net.minecraft.network.protocol.game.ServerboundChatPacket
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

	// The player these events are for
	@Shadow
	public Player player;

	/**
	 * Runs when a player has sent a chat message.
	 * This will run for signed & unsigned messages. Signed messages have a valid .signature().
	 * @param packet Data about the chat message. Includes content, signature, etc.
	 * @see com.viral32111.events.callback.server.PlayerChatMessageCallback
	 * @since 0.1.0
	 */
	@Inject(method = "onChatMessage", at = @At("TAIL"))
	private void viral32111_events_onChatMessage(ServerboundChatPacket packet, CallbackInfo callbackInfo) {

		// Invoke all listeners of this mixin's callback.
		// No need to check the listener results as this mixin cannot be cancelled.
		PlayerChatMessageCallback.Companion.getEVENT().invoker().interact(player, packet);

	}

}
