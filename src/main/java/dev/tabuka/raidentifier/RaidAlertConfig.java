package dev.tabuka.raidentifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaidAlertConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger("RaidIdentifier");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final File CONFIG_DIR = FabricLoader.getInstance().getConfigDir().toFile();
	private static final File CONFIG_FILE = new File(CONFIG_DIR, "raidentifier.json");

	public static class ConfigData {
		public double detectionRange = 32.0;
		public float volume = 1.0f;
		public boolean enabled = true;
		public boolean playSound = true;
		public String soundType = "bell"; // bell, alarm, beep
		public int alertCooldown = 5; // seconds
	}

	private static ConfigData config = new ConfigData();

	public static void loadConfig() {
		if (CONFIG_FILE.exists()) {
			try (FileReader reader = new FileReader(CONFIG_FILE)) {
				config = GSON.fromJson(reader, ConfigData.class);
				LOGGER.info("Config loaded from " + CONFIG_FILE.getAbsolutePath());
			} catch (IOException e) {
				LOGGER.error("Error loading config", e);
				config = new ConfigData();
			}
		} else {
			saveConfig();
		}
	}

	public static void saveConfig() {
		CONFIG_DIR.mkdirs();
		try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
			GSON.toJson(config, writer);
			LOGGER.info("Config saved to " + CONFIG_FILE.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error("Error saving config", e);
		}
	}

	public static ConfigData getConfig() {
		return config;
	}

	public static void setConfig(ConfigData newConfig) {
		config = newConfig;
		saveConfig();
	}
}
