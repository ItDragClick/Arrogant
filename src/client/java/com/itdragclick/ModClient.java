package com.itdragclick;

import com.itdragclick.Events.LagbackCheck;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

import static com.itdragclick.ToggleCocoa.isRunningCocoa;
import static com.itdragclick.ToggleMelon.isRunningMelon;
import static com.itdragclick.ToggleNetherWarts.isRunningWarts;

public class ModClient implements ClientModInitializer {
	public static KeyBinding ToggleMelonBinding;
	public static KeyBinding ToggleCocoaBinding;
	public static KeyBinding ToggleWartsBinding;
	public static KeyBinding ToggleDebugBinding;
	public static KeyBinding ServerInfoBinding;
	public static boolean debug = false;
//	private boolean isRunning = false;
//	private double NormalY;
	public static String ModName = "§cArrogant§r §8»§7 ";

	private World previousWorldState;

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		WorldTickCallback.EVENT.register((world) -> {
			if(MinecraftClient.getInstance().isInSingleplayer()){
				return;
			}
//			if (debug) System.out.println("Changing World");
			if (!world.equals(previousWorldState)) {
				// The world has changed, run your code here
				if (debug) System.out.println("Not Same World!");
				if(isRunningMelon || isRunningCocoa || isRunningWarts){
					isRunningMelon = false;
					isRunningCocoa = false;
					isRunningWarts = false;
					ToggleMelon.isTurning = false;
					ToggleCocoa.isTurning = false;
					ToggleNetherWarts.isTurning = false;
					ToggleMelon.isDoneMelon = false;
					ToggleCocoa.isDoneCocoa = false;
					ToggleNetherWarts.isDoneWarts = false;
					LagbackCheck.inLastLag = false;
					MinecraftClient.getInstance().player.sendMessage(Text.of(ModName+"§dMacro §7[§cERROR: x000W§7]"));
					MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.VOICE, 100.0F, 1.0F);
					MinecraftClient.getInstance().options.attackKey.setPressed(false);
					MinecraftClient.getInstance().options.forwardKey.setPressed(false);
					MinecraftClient.getInstance().options.leftKey.setPressed(false);
					MinecraftClient.getInstance().options.rightKey.setPressed(false);
					MinecraftClient.getInstance().options.backKey.setPressed(false);
					reconnect_garden();
				}
			}
			previousWorldState = world;
		});
		ToggleMelonBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Melon (Toggle)",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F4,
				"Arrogant"
		));
		ToggleCocoaBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Cocoa (Toggle)",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F5,
				"Arrogant"
		));
		ToggleWartsBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"NetherWarts (Toggle)",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F6,
				"Arrogant"
		));
		ToggleDebugBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Debug Mode",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F7,
				"Arrogant"
		));
		ServerInfoBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Server Info",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F8,
				"Arrogant"
		));
		ClientTickCallback.EVENT.register(client -> {
			if (ToggleDebugBinding.wasPressed()) {
				debug = !debug; // Toggle the debug value
				MinecraftClient.getInstance().player.sendMessage(Text.of(ModName+"§dDebug Mode " + (debug ? "§7[§aEnabled§7]" : "§7[§cDisabled§7]")));
			}
			if (ServerInfoBinding.wasPressed()) {
				ServerInfo serverInfo = client.getCurrentServerEntry();
				if (serverInfo != null) {
					String serverIP = serverInfo.address;
					String serverName = serverInfo.name;

					client.player.sendMessage(Text.of("Server IP: " + serverIP));
					client.player.sendMessage(Text.of("Server Name: " + serverName));
					client.player.sendMessage(Text.of("Server Version: " + serverInfo.version));
					client.player.sendMessage(Text.of("World Name: " + client.world.getRegistryKey().getValue().toString()));
				} else if (client.isInSingleplayer()) {
					client.player.sendMessage(Text.of("You are in singleplayer mode."));
					client.player.sendMessage(Text.of("World Name: " + client.world.getRegistryKey().getValue().toString()));
				}
			}
			ToggleMelon.handleToggle(client);
			ToggleCocoa.handleToggle(client);
			ToggleNetherWarts.handleToggle(client);
		});
		new LagbackCheck();
	}

	private void reconnect_garden(){

	}
}