package dev.tabuka.raidentifier;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class RaidAlertScreen extends Screen {
	private TextFieldWidget detectionRangeField;
	private TextFieldWidget volumeField;
	private TextFieldWidget cooldownField;
	private ButtonWidget enableButton;
	private ButtonWidget soundToggleButton;
	private ButtonWidget soundTypeButton;
	private ButtonWidget saveButton;

	public RaidAlertScreen() {
		super(Text.literal("RaidIdentifier - Settings"));
	}

	@Override
	protected void init() {
		int centerX = this.width / 2;
		int startY = 30;
		int spacing = 30;
		int fieldWidth = 100;

		RaidAlertConfig.ConfigData config = RaidAlertConfig.getConfig();

		this.detectionRangeField = new TextFieldWidget(this.textRenderer, centerX - fieldWidth / 2, startY, fieldWidth, 20, Text.literal("Detection Range"));
		this.detectionRangeField.setText(String.valueOf(config.detectionRange));
		this.addDrawableChild(this.detectionRangeField);

		this.volumeField = new TextFieldWidget(this.textRenderer, centerX - fieldWidth / 2, startY + spacing, fieldWidth, 20, Text.literal("Volume"));
		this.volumeField.setText(String.valueOf(config.volume));
		this.addDrawableChild(this.volumeField);

		this.cooldownField = new TextFieldWidget(this.textRenderer, centerX - fieldWidth / 2, startY + spacing * 2, fieldWidth, 20, Text.literal("Cooldown (s)"));
		this.cooldownField.setText(String.valueOf(config.alertCooldown));
		this.addDrawableChild(this.cooldownField);

		String enableText = config.enabled ? "§a[ON]" : "§c[OFF]";
		this.enableButton = ButtonWidget.builder(Text.literal("Alert: " + enableText), (button) -> {
			config.enabled = !config.enabled;
			String newText = config.enabled ? "§a[ON]" : "§c[OFF]";
			button.setMessage(Text.literal("Alert: " + newText));
		}).dimensions(centerX - 80, startY + spacing * 3, 160, 20).build();
		this.addDrawableChild(this.enableButton);

		String soundText = config.playSound ? "§a[ON]" : "§c[OFF]";
		this.soundToggleButton = ButtonWidget.builder(Text.literal("Sound: " + soundText), (button) -> {
			config.playSound = !config.playSound;
			String newText = config.playSound ? "§a[ON]" : "§c[OFF]";
			button.setMessage(Text.literal("Sound: " + newText));
		}).dimensions(centerX - 80, startY + spacing * 4, 160, 20).build();
		this.addDrawableChild(this.soundToggleButton);

		this.soundTypeButton = ButtonWidget.builder(Text.literal("Sound Type: " + config.soundType), (button) -> {
			if (config.soundType.equals("bell")) {
				config.soundType = "alarm";
			} else if (config.soundType.equals("alarm")) {
				config.soundType = "beep";
			} else {
				config.soundType = "bell";
			}
			button.setMessage(Text.literal("Sound Type: " + config.soundType));
		}).dimensions(centerX - 80, startY + spacing * 5, 160, 20).build();
		this.addDrawableChild(this.soundTypeButton);

		this.saveButton = ButtonWidget.builder(Text.literal("§a✓ Save Settings"), (button) -> {
			try {
				config.detectionRange = Double.parseDouble(this.detectionRangeField.getText());
				config.volume = Float.parseFloat(this.volumeField.getText());
				config.alertCooldown = Integer.parseInt(this.cooldownField.getText());

				config.detectionRange = Math.max(5.0, Math.min(128.0, config.detectionRange));
				config.volume = Math.max(0.0f, Math.min(1.0f, config.volume));
				config.alertCooldown = Math.max(1, Math.min(60, config.alertCooldown));

				RaidAlertConfig.setConfig(config);
				this.client.setScreen(null);
			} catch (NumberFormatException e) {
				RaidIdentifier.LOGGER.error("Invalid input", e);
			}
		}).dimensions(centerX - 80, startY + spacing * 6, 160, 20).build();
		this.addDrawableChild(this.saveButton);

		this.addDrawableChild(ButtonWidget.builder(Text.literal("§cClose"), (button) -> {
			this.client.setScreen(null);
		}).dimensions(centerX - 80, startY + spacing * 7, 160, 20).build());
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context, mouseX, mouseY, delta);
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);

		context.drawTextWithShadow(this.textRenderer, Text.literal("Detection Range (blocks):"), this.width / 2 - 100, 35, 0xAAAAFF);
		context.drawTextWithShadow(this.textRenderer, Text.literal("Volume (0.0-1.0):"), this.width / 2 - 100, 65, 0xAAAAFF);
		context.drawTextWithShadow(this.textRenderer, Text.literal("Alert Cooldown (1-60s):"), this.width / 2 - 100, 95, 0xAAAAFF);

		super.render(context, mouseX, mouseY, delta);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
}
