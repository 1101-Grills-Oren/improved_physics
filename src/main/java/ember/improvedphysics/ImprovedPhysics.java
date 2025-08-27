package ember.improvedphysics;

import net.fabricmc.api.ModInitializer;
import ember.improvedphysics.ModCommands;
import ember.improvedphysics.ModBlocks;
import ember.improvedphysics.ModEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ember.improvedphysics.CommandTriggerPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ImprovedPhysics implements ModInitializer {
	public static final String MOD_ID = "improved_physics";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModCommands.initialize();
		ModBlocks.initialize();
		ModEffects.initialize();
		LOGGER.info("Hello Fabric world!");
		ServerPlayNetworking.registerGlobalReceiver(CommandTriggerPacket.PACKET_ID, (server,player, handler, buf, responseSender) -> {
    		// Read packet data on the event loop
		    String command = buf.readString();
		    server.execute(() -> {
        		LOGGER.info("Recieved custom packet with command "+command);
        		server.getCommandManager().executeWithPrefix(player.getCommandSource(),command);
	    	});
		});
	}
}