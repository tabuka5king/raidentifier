package dev.tabuka.raidentifier;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaidAlertManager {
	private static final Logger LOGGER = LoggerFactory.getLogger("RaidIdentifier");
	private static final Map<String, Long> lastAlertTime = new HashMap<>();
	private static long lastGeneralAlert = 0;
	private static final HttpClient HTTP = HttpClient.newHttpClient();

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
		double px = client.player.getX();
		double py = client.player.getY();
		double pz = client.player.getZ();

		int nearbyCount = 0;
		String nearestName = null;
		double nearestDist = Double.MAX_VALUE;

		for (PlayerEntity player : client.world.getPlayers()) {
			if (player == client.player || player.isInvisible()) {
				continue;
			}

			double dx = px - player.getX();
			double dy = py - player.getY();
			double dz = pz - player.getZ();
			double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

			if (distance <= detectionRange) {
				String playerName = player.getName().getString();
				long currentTime = System.currentTimeMillis();
				long lastAlert = lastAlertTime.getOrDefault(playerName, 0L);

				if (currentTime - lastAlert >= alertCooldown) {
					triggerAlert(playerName, distance);
					lastAlertTime.put(playerName, currentTime);
				}

				nearbyCount++;
				if (distance < nearestDist) {
					nearestDist = distance;
					nearestName = playerName;
				}
			}
		}

		// Telefon ertesites - globalis cooldown, hogy zsufolt szerveren ne spameljen
		if (nearbyCount > 0 && nearestName != null) {
			long now = System.currentTimeMillis();
			if (now - lastGeneralAlert >= alertCooldown) {
				sendPhoneNotification(nearestName, nearestDist, nearbyCount);
				lastGeneralAlert = now;
			}
		}
	}

	private static void sendPhoneNotification(String nearestName, double distance, int count) {
		RaidAlertConfig.ConfigData cfg = RaidAlertConfig.getConfig();
		if (!cfg.phoneNotify || cfg.ntfyTopic == null || cfg.ntfyTopic.isBlank()) {
			return;
		}

		try {
			String body = (count == 1)
				? "Jatekos a kozelben: " + nearestName + " (" + String.format("%.0f", distance) + " blokk)"
				: count + " jatekos a kozelben! Legkozelebb: " + nearestName + " (" + String.format("%.0f", distance) + " blokk)";

			HttpRequest req = HttpRequest.newBuilder()
				.uri(URI.create("https://ntfy.sh/" + cfg.ntfyTopic.trim()))
				.header("Title", "RAID ALERT")
				.header("Priority", "urgent")
				.header("Tags", "rotating_light")
				.POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
				.build();

			HTTP.sendAsync(req, HttpResponse.BodyHandlers.discarding());
		} catch (Exception e) {
			LOGGER.error("Phone notification failed", e);
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
