package dev.tabuka.raidentifier;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaidAlertManager {
	private static final Logger LOGGER = LoggerFactory.getLogger("RaidIdentifier");
	private static final Map<String, Long> lastAlertTime = new HashMap<>();
	private static long lastGeneralAlert = 0;

	public static void init() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> tick());
	}

	private static void tick() {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.world == null || client.player == null || !RaidAlertConfig.getConfig().enabled) {
			return;
		}

		double detectionRange = RaidAlertConfig.getConfig().detectionRange;
		long alertCooldown = (long) RaidAlertConfig.getConfig().alertCooldown * 1000;
		Vec3d playerPos = client.player.getPos();

		for (PlayerEntity player : client.world.getPlayers()) {
			if (player == client.player || player.isInvisible()) {
				continue;
			}

			double distance = playerPos.distanceTo(player.getPos());

			if (distance <= detectionRange) {
				String playerName = player.getName().getString();
				long currentTime = System.currentTimeMillis();
				long lastAlert = lastAlertTime.getOrDefault(playerName, 0L);

				if (currentTime - lastAlert >= alertCooldown) {
					triggerAlert(playerName, distance);
					lastAlertTime.put(playerName, currentTime);
				}
			}
		}
	}

	private static void triggerAlert(String playerName, double distance) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) {
			return;
		}

		if (RaidAlertConfig.getConfig().playSound) {
			SoundEvent soundEvent = getSoundEvent();
			float volume = RaidAlertConfig.getConfig().volume;

			client.player.playSound(soundEvent, volume, 2.0f);

			if (volume > 0.7f) {
				client.player.playSound(soundEvent, volume, 1.5f);
			}
		}

		LOGGER.warn("RAID ALERT! Player '" + playerName + "' detected at distance: " + String.format("%.1f", distance) + " blocks!");
	}

	private static SoundEvent getSoundEvent() {
		String soundType = RaidAlertConfig.getConfig().soundType;
		return switch (soundType) {
			case "alarm" -> SoundEvents.BLOCK_ANVIL_FALL;
			case "beep" -> SoundEvents.BLOCK_COMPARATOR_CLICK;
			default -> SoundEvents.BLOCK_NOTE_BLOCK_BELL.value();
		};
	}
}
