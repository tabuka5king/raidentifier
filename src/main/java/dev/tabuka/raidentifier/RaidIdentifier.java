package dev.tabuka.raidentifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaidIdentifier implements ClientModInitializer {
	public static final String MOD_ID = "raidentifier";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static KeyBinding openMenuKey;
	private static final KeyBinding.Category CATEGORY =
		KeyBinding.Category.create(Identifier.of(MOD_ID, "main"));

	@Override
	public void onInitializeClient() {
		LOGGER.info("RaidIdentifier mod initializing...");

		RaidAlertConfig.loadConfig();
		RaidAlertManager.init();

		openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.raidentifier.open_menu",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_INSERT,
			CATEGORY
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openMenuKey.wasPressed()) {
				client.setScreen(new RaidAlertScreen());
			}
		});

		LOGGER.info("RaidIdentifier mod initialized successfully!");
	}
}
